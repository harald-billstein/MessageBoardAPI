package billstein.harald.chatApi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Data
@Controller
public class ProfanityFilter {

  // IM AM SORRY!
  private static List<String> badWords = new ArrayList<>(Arrays.asList(
      "damn",
      "dick",
      "fuck"
  ));
  private Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);

  public boolean isProfanityFree(String message) {

    boolean messageClean = true;
    boolean test;

    for (String badWord : badWords) {
      test = message.contains(badWord);

      if (test) {
        messageClean = false;
        break;
      }

    }
    return messageClean;
  }
}
