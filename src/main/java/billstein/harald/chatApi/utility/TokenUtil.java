package billstein.harald.chatApi.utility;

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
}
