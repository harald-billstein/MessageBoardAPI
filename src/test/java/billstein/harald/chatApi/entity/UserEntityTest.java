package billstein.harald.chatApi.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserEntityTest {

  private Set<MessageEntity> messages;
  private UserEntity userEntity;
  private final String salt = "salt";
  private final String hashedPassword = "hashedpassword";
  private final boolean admin = true;
  private final String userName = "username";
  private final String token = "token";
  private long tokenCreated;
  private final String message = "message";
  private final int userID = 1;

  @Before
  public void init() {
    userEntity = new UserEntity();
    userEntity.setSalt(salt);
    userEntity.setHaschedPassword(hashedPassword);
    userEntity.setAdmin(admin);
    userEntity.setUserName(userName);
    userEntity.setToken(token);
    tokenCreated = new Date().getTime();
    userEntity.setWhenTokenWasCreated(tokenCreated);
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setMessageContent(message);
    messages = new HashSet<>();
    messages.add(messageEntity);
    userEntity.setMessage(messages);
    userEntity.setUserID(userID);
  }

  @Test
  public void saltTest() {
    Assert.assertEquals(salt, userEntity.getSalt());

  }

  @Test
  public void hashedPasswordTest() {
    Assert.assertEquals(hashedPassword, userEntity.getHaschedPassword());

  }

  @Test
  public void adminTest() {
    Assert.assertEquals(admin, userEntity.isAdmin());
  }

  @Test
  public void userNameTest() {
    Assert.assertEquals(userName, userEntity.getUserName());
  }

  @Test
  public void tokenTest() {
    Assert.assertEquals(token, userEntity.getToken());
  }

  @Test
  public void setTokenCreatedTest() {
    Assert.assertEquals(tokenCreated, userEntity.getWhenTokenWasCreated());
  }

  @Test
  public void messageTest() {
    String userMessage = null;

    if (userEntity.getMessage().stream().findFirst().isPresent()) {
      userMessage = userEntity.getMessage().stream().findFirst().get().getMessageContent();
    }

    Assert.assertEquals(message, userMessage);
  }

  @Test
  public void userIDTest() {
    Assert.assertEquals(userID, userEntity.getUserID().intValue());
  }


}
