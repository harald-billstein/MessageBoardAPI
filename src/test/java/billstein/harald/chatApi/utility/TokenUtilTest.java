package billstein.harald.chatApi.utility;

import billstein.harald.chatApi.entity.UserEntity;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

public class TokenUtilTest {


  @Test
  public void generateTokenTest() {
    String token = TokenUtil.generateToken();
    Assert.assertNotNull(token);
  }

  @Test
  public void generateTimeTest() {
    long time = TokenUtil.generateTime();
    Assert.assertNotNull(time);
  }

  @Test
  public void tokenExpiredTest() {
    UserEntity user = new UserEntity();

    user.setWhenTokenWasCreated(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(23));
    Assert.assertFalse(TokenUtil.hasTokenExpired(user));

    user.setWhenTokenWasCreated(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24));
    Assert.assertTrue(TokenUtil.hasTokenExpired(user));
  }

}
