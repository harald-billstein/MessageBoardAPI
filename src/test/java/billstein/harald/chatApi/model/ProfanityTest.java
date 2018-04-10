package billstein.harald.chatApi.model;

import billstein.harald.chatApi.utility.TokenUtil;
import org.junit.Assert;
import org.junit.Test;

public class ProfanityTest {

  @Test
  public void testIncomingProfanityRequest() {

    IncomingProfanityRequest incomingProfanityRequest = new IncomingProfanityRequest();
    String token = TokenUtil.generateToken();
    String userName = "testName";
    String word = "word";

    incomingProfanityRequest.setUserName(userName);
    incomingProfanityRequest.setToken(token);
    incomingProfanityRequest.setWord(word);

    Assert.assertEquals(userName, incomingProfanityRequest.getUserName());
    Assert.assertEquals(token, incomingProfanityRequest.getToken());
    Assert.assertEquals(word, incomingProfanityRequest.getWord());
  }

  @Test
  public void testOutgoingProfanityRequest() {
    OutgoingProfanityRequest outgoingProfanityRequest = new OutgoingProfanityRequest();

    String message = "message";
    outgoingProfanityRequest.setMessage(message);
    outgoingProfanityRequest.setSuccess(true);

    Assert.assertEquals(message, outgoingProfanityRequest.getMessage());
    Assert.assertTrue(outgoingProfanityRequest.isSuccess());

  }

}
