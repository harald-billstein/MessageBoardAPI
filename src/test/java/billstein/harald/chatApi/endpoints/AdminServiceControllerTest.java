package billstein.harald.chatApi.endpoints;

import billstein.harald.chatApi.ChatApiApplication;
import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.model.IncomingProfanityRequest;
import billstein.harald.chatApi.model.OutgoingProfanityRequest;
import billstein.harald.chatApi.persistent.ProfanityIO;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApiApplication.class)
public class AdminServiceControllerTest {

  @Autowired
  private AdminServiceController adminServiceController;
  @Autowired
  private UserHandler userHandler;
  @Autowired
  private ProfanityIO profanityIO;

  @Test
  public void profanityIOTest() {

    UserEntity userEntity = userHandler.getUser("Harald");

    IncomingProfanityRequest request = new IncomingProfanityRequest();
    request.setWord("testWord");
    request.setUserName(userEntity.getUserName());
    request.setToken(userEntity.getToken());

    ResponseEntity<OutgoingProfanityRequest> response = adminServiceController
        .addBannedWord(request);

    List<String> words = profanityIO.loadBannedWordsFromXML();

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(request.getWord(), words.get((words.size() - 1)));

    adminServiceController.removeBannedWord(request);
    words = profanityIO.loadBannedWordsFromXML();

    Assert.assertNotEquals(request.getWord(), words.get((words.size() - 1)));

  }

}
