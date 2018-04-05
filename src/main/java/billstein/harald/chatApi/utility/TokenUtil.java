package billstein.harald.chatApi.utility;

import billstein.harald.chatApi.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtil {

  public static String generateToken() {
    int length = 64;
    boolean useLetters = true;
    boolean useNumbers = true;

    return RandomStringUtils.random(length, useLetters, useNumbers);
  }

  public static long generateTime() {
    return System.currentTimeMillis();
  }

  public static boolean hasTokenExpired(UserEntity userEntity) {

    long createDate = userEntity.getWhenTokenWasCreated();
    long currentTime = System.currentTimeMillis();
    long oneDayInMilliseconds = 86400000;

    long tokenAge = oneDayInMilliseconds + createDate;
    long result = currentTime - tokenAge;

    return result >= 0;
  }

  public static boolean refreshToken() {

    return false;
  }
}
