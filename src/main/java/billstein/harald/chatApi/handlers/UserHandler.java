package billstein.harald.chatApi.handlers;

import billstein.harald.chatApi.persistent.UserRepository;
import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.model.IncomingUser;
import billstein.harald.chatApi.model.OutgoingUser;
import billstein.harald.chatApi.utility.PasswordUtil;
import billstein.harald.chatApi.utility.TokenUtil;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class UserHandler {

  private Logger logger = LoggerFactory.getLogger(UserHandler.class);
  private UserRepository userRepository;

  public UserHandler(UserRepository userRepository) {
    logger.info("UserHandler created");
    this.userRepository = userRepository;
  }

  public boolean isValidUser(String name) {

    Iterable<UserEntity> users = getAllUsers();

    for (UserEntity userEntity : users) {
      if (userEntity.getUserName().equals(name)) {
        return true;
      }
    }

    return false;
  }

  public boolean userHasAccess(List<String> possibleMatches, String token) {

    boolean hasAccess = false;

    for (String possibleMatch : possibleMatches) {
      if (possibleMatch.equals(token)) {
        hasAccess = true;
        break;
      }
    }

    return hasAccess;
  }

  public boolean tokenIsValied(String token, UserEntity userEntity) {
    return userEntity != null && token.equals(userEntity.getToken());
  }

  public UserEntity getUser(String name) {

    Iterable<UserEntity> users = getAllUsers();
    UserEntity selectedUser = null;

    for (UserEntity userEntity : users) {
      if (userEntity.getUserName().equals(name)) {
        selectedUser = userEntity;
        break;
      }
    }

    return selectedUser;
  }

  public OutgoingUser createOutgoingUser(UserEntity savedUser) {
    OutgoingUser outgoingUser = new OutgoingUser();
    outgoingUser.setUsername(savedUser.getUserName());
    outgoingUser.setToken(savedUser.getToken());
    return outgoingUser;
  }

  public UserEntity saveNewUser(IncomingUser user) {
    String salt = RandomStringUtils.randomAlphabetic(10);
    String token = PasswordUtil.createHashedPassword(user.getPassWord(), salt);

    UserEntity userToBeSaved = new UserEntity();

    userToBeSaved.setUserName(user.getUserName());
    userToBeSaved.setSalt(salt);
    userToBeSaved.setHaschedPassword(token);
    userToBeSaved.setAdmin(false);
    userToBeSaved.setToken(TokenUtil.generateToken());
    userToBeSaved.setWhenTokenWasCreated(TokenUtil.generateTime());

    try {
      userRepository.save(userToBeSaved);
      return userToBeSaved;
    } catch (Exception e) {
      return null;
    }
  }

  private Iterable<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  public boolean deleteUser(UserEntity userEntity) {
    boolean success;

    try {
      userRepository.delete(userEntity);
      success = true;
    } catch (IllegalArgumentException e) {
      success = false;
    }
    return success;
  }
}
