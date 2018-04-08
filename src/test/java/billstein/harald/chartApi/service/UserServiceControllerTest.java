package billstein.harald.chartApi.service;

import billstein.harald.chatApi.ChatApiApplication;
import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.model.IncomingUser;
import billstein.harald.chatApi.model.OutgoingUser;
import billstein.harald.chatApi.service.UserServiceController;
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
public class UserServiceControllerTest {

  @Autowired
  private UserServiceController userServiceController;
  @Autowired
  private UserHandler userHandler;
  private IncomingUser incomingUser;
  private UserEntity userEntity;
  private String userName = "JunitTestUser";
  private String userPassword = "JunitTestPassword";

  @Before
  public void setupNewUser() {
    incomingUser = new IncomingUser();
    incomingUser.setUserName(userName);
    incomingUser.setPassWord(userPassword);
    userEntity = userHandler.saveNewUser(incomingUser);
  }

  @After
  public void cleanupNewUser() {
    if (userEntity != null) {
      userHandler.deleteUser(userEntity);
    }
  }

  @Test
  public void getUser() {
    ResponseEntity<OutgoingUser> outgoingUser = userServiceController.getUser(incomingUser);

    Assert.assertEquals(HttpStatus.OK, outgoingUser.getStatusCode());
    Assert.assertEquals(outgoingUser.getBody().getUsername(), userName);
    Assert.assertNotNull(outgoingUser.getBody().getToken());

    incomingUser.setPassWord(null);
    outgoingUser = userServiceController.getUser(incomingUser);
    Assert.assertEquals(outgoingUser.getStatusCode(), HttpStatus.NOT_FOUND);

    incomingUser.setPassWord("wrongPassword");
    outgoingUser = userServiceController.getUser(incomingUser);
    Assert.assertEquals(outgoingUser.getStatusCode(), HttpStatus.NOT_FOUND);

    outgoingUser = userServiceController.getUser(new IncomingUser());

    Assert.assertEquals(outgoingUser.getStatusCode(), HttpStatus.NOT_FOUND);

  }

  @Test
  public void deleteUser() {
    ResponseEntity<Boolean> response = userServiceController.deleteUser(incomingUser);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    response = userServiceController.deleteUser(incomingUser);
    Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

  }

  @Test
  public void addUser() {
    IncomingUser incomingUser = new IncomingUser();
    incomingUser.setUserName("testName");
    incomingUser.setPassWord("testPassword");

    ResponseEntity<OutgoingUser> PostUserResponse = userServiceController.postUser(incomingUser);
    Assert.assertEquals(PostUserResponse.getStatusCode(), HttpStatus.OK);

    PostUserResponse = userServiceController.postUser(incomingUser);
    Assert.assertEquals(PostUserResponse.getStatusCode(), HttpStatus.IM_USED);

    ResponseEntity<Boolean> deleteUserResponse = userServiceController.deleteUser(incomingUser);
    Assert.assertEquals(deleteUserResponse.getBody(), true);

    deleteUserResponse = userServiceController.deleteUser(incomingUser);
    Assert.assertEquals(deleteUserResponse.getBody(), false);


  }

}
