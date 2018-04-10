package billstein.harald.chatApi.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageEntityTest {

  private UserEntity userEntity;
  private MessageEntity messageEntity;
  private final String message = "message";
  private final int messageID = 1;
  private final String userName = "username";

  @Before
  public void init() {
    messageEntity = new MessageEntity();
    messageEntity.setMessageContent(message);
    messageEntity.setMessageID(messageID);
    userEntity = new UserEntity();
    userEntity.setUserName(userName);
    messageEntity.setUser(userEntity);
  }

  @Test
  public void messageContentTest() {
    Assert.assertEquals(message, messageEntity.getMessageContent());
  }

  @Test
  public void messageIDTest() {
    Assert.assertEquals(messageID, messageEntity.getMessageID().intValue());
  }

  @Test
  public void Test() {
    Assert.assertEquals(userName, messageEntity.getUser().getUserName());
  }

}
