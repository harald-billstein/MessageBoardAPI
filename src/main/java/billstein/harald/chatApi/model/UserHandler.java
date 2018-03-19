package billstein.harald.chatApi.model;

import billstein.harald.chatApi.Entity.UserEntity;
import billstein.harald.chatApi.repository.UserRepository;
import java.util.List;
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

  private Iterable<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }
}
