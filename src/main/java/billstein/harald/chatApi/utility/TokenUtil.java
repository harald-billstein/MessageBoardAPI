package billstein.harald.chatApi.utility;

import billstein.harald.chatApi.entity.UserEntity;
import java.util.Calendar;
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
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(userEntity.getWhenTokenWasCreated());

    long createDate = userEntity.getWhenTokenWasCreated();
    long currentTime = System.currentTimeMillis();
    long oneDay = 86400000;
    //long tenSeconds = 10000;

    long tokenAge = oneDay + createDate;
    long result = currentTime - tokenAge;

    System.out.println(result);

    return result >= 0;
  }
}
