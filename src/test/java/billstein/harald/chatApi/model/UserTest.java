package billstein.harald.chatApi.model;

import billstein.harald.chatApi.utility.TokenUtil;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {

  private String userName = "testName";
  private String password = "testPassword";

  @Test
  public void testIncomingUser() {
    IncomingUser incomingUser = new IncomingUser();
    incomingUser.setUserName(userName);
    incomingUser.setPassWord(password);

    Assert.assertEquals(userName, incomingUser.getUserName());
    Assert.assertEquals(password, incomingUser.getPassWord());
  }

  @Test
  public void testOutgoingUser() {

    String token = TokenUtil.generateToken();

    OutgoingUser outgoingUser = new OutgoingUser();
    outgoingUser.setUsername(userName);
    outgoingUser.setToken(token);

    Assert.assertEquals(userName, outgoingUser.getUsername());
    Assert.assertEquals(token, outgoingUser.getToken());
  }

}
