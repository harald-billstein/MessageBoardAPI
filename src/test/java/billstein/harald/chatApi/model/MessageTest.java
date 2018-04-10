package billstein.harald.chatApi.model;

import billstein.harald.chatApi.utility.TokenUtil;
import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

  private final String message = "testMessage";
  private final String user = "testName";

  @Test
  public void testIncomingMessage() {

    String token = TokenUtil.generateToken();

    IncomingMessage incomingMessage = new IncomingMessage();
    incomingMessage.setMessage(message);
    incomingMessage.setToken(token);
    incomingMessage.setUser(user);

    Assert.assertEquals(message, incomingMessage.getMessage());
    Assert.assertEquals(token, incomingMessage.getToken());
    Assert.assertEquals(user, incomingMessage.getUser());
  }

  @Test
  public void testOutgoingMessage() {
    OutgoingMessage outgoingMessage = new OutgoingMessage();
    outgoingMessage.setMessage(message);
    outgoingMessage.setUser(user);

    Assert.assertEquals(message, outgoingMessage.getMessage());
    Assert.assertEquals(user, outgoingMessage.getUser());

  }


}
