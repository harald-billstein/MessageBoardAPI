package billstein.harald.chatApi.endpoints;

import static billstein.harald.chatApi.utility.PasswordUtil.getPossibleMatches;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.model.IncomingUser;
import billstein.harald.chatApi.model.OutgoingUser;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v2/users")
public class UserServiceController {

  private final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
  private final UserHandler userHandler;

  public UserServiceController(UserHandler userHandler) {
    this.userHandler = userHandler;
  }

  @PostMapping(path = "/user/create")
  public ResponseEntity<OutgoingUser> postUser(@RequestBody() IncomingUser user) {
    logger.info("Register new user");

    UserEntity savedUser = userHandler.saveNewUser(user);
    if (savedUser != null) {
      OutgoingUser outgoingUser = userHandler.createOutgoingUser(savedUser);
      return ResponseEntity.ok(outgoingUser);
    } else {
      return ResponseEntity.status(HttpStatus.IM_USED).build();
    }
  }

  @GetMapping(path = "/user/retrieve")
  public ResponseEntity<OutgoingUser> getUser(@RequestParam String userName,
      @RequestParam String password) {
    logger.info("Retrieving user");

    UserEntity userEntity;
    List<String> listOfPossibleHashedPasswords;
    String hashedPassword;
    boolean accessGranted;
    OutgoingUser outgoingUser;

    try {
      userEntity = userHandler.getUser(userName);
      listOfPossibleHashedPasswords = getPossibleMatches(password, userEntity.getSalt());
      hashedPassword = userEntity.getHaschedPassword();
      accessGranted = userHandler.userHasAccess(listOfPossibleHashedPasswords, hashedPassword);
      outgoingUser = userHandler.createOutgoingUser(userEntity);
    } catch (Exception e) {
      accessGranted = false;
      outgoingUser = null;
    }

    if (accessGranted && outgoingUser != null) {
      return ResponseEntity.ok(outgoingUser);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping(path = "/user/remove")
  public ResponseEntity<Boolean> deleteUser(@RequestBody() IncomingUser user) {
    logger.info("delete user: " + user.getUserName() + " " + user.getPassWord());

    UserEntity userEntity;
    List<String> listOfPossibleHashedPasswords;
    String hashedPassword;
    boolean accessGranted;

    userEntity = userHandler.getUser(user.getUserName());

    try {
      if (userEntity != null) {
        listOfPossibleHashedPasswords = getPossibleMatches(user.getPassWord(),
            userEntity.getSalt());
        hashedPassword = userEntity.getHaschedPassword();
        accessGranted = userHandler.userHasAccess(listOfPossibleHashedPasswords, hashedPassword);
      } else {
        accessGranted = false;
      }
    } catch (NoSuchAlgorithmException e) {
      accessGranted = false;
    }

    if (accessGranted) {
      userHandler.deleteUser(userEntity);
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }
}
