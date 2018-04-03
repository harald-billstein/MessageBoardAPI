package billstein.harald.chatApi.model;

import billstein.harald.chatApi.utility.TokenUtil;
import org.junit.Assert;
import org.junit.Test;

public class ProfanityTest {

  private String userName = "testName";
  private String word = "word";
  private String message = "message";
  private boolean success = true;

  @Test
  public void testIncomingProfanityRequest() {

    String token = TokenUtil.generateToken();

    IncomingProfanityRequest incomingProfanityRequest = new IncomingProfanityRequest();
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
    outgoingProfanityRequest.setMessage(message);
    outgoingProfanityRequest.setSuccess(success);

    Assert.assertEquals(message, outgoingProfanityRequest.getMessage());
    Assert.assertEquals(success, outgoingProfanityRequest.isSuccess());

  }

}
