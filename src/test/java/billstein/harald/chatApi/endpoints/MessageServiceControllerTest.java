package billstein.harald.chatApi.endpoints;

import billstein.harald.chatApi.ChatApiApplication;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.IncomingUser;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.model.OutgoingUser;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApiApplication.class)
public class MessageServiceControllerTest {

  @Autowired
  UserServiceController userServiceController;
  @Autowired
  private MessageServiceController messageServiceController;
  private IncomingUser incomingUser;
  private final String userName = "JunitTestUser";
  private final String userPassword = "JunitTestPassword";
  private ResponseEntity<OutgoingUser> userEntity;

  @Before
  public void init() {
    incomingUser = new IncomingUser();
    incomingUser.setUserName(userName);
    incomingUser.setPassWord(userPassword);
    userEntity = userServiceController.postUser(incomingUser);
  }

  @After
  public void cleanup() {
    userServiceController.deleteUser(incomingUser);
  }

  @Test
  public void getLatestMessagesTest() {
    ResponseEntity<List<OutgoingMessage>> messages;
    messages = messageServiceController
        .getLatestMessages(userName, userEntity.getBody().getToken());
    Assert.assertNotNull(messages);
  }

  @Test
  public void sendMessageTest() {
    IncomingMessage incomingMessage = new IncomingMessage();
    incomingMessage.setUser(userEntity.getBody().getUsername());
    incomingMessage.setToken(userEntity.getBody().getToken());
    incomingMessage.setMessage("TestMessage");
    ResponseEntity<OutgoingMessage> response = messageServiceController
        .sendMessage(incomingMessage);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

  }

}
