package billstein.harald.chatApi.service;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.model.IncomingUser;
import billstein.harald.chatApi.database.UserRepository;
import billstein.harald.chatApi.utility.PasswordUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v2")
public class UserServiceController {

  private Logger logger = LoggerFactory.getLogger(UserServiceController.class);
  private UserRepository userRepository;

  public UserServiceController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping(path = "/user/new")
  public ResponseEntity<IncomingUser> postUser(@RequestBody() IncomingUser user) {
    logger.info("postUser");

    String salt = RandomStringUtils.randomAlphabetic(10);
    String token = PasswordUtil.createHashedPassword(user.getPassWord(), salt);

    UserEntity userToBeSaved = new UserEntity();

    userToBeSaved.setUserName(user.getUserName());
    userToBeSaved.setSalt(salt);
    userToBeSaved.setToken(token);

    try {
      userRepository.save(userToBeSaved);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.IM_USED).body(user);
    }
  }
}
