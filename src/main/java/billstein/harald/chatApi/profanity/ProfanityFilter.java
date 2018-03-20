package billstein.harald.chatApi.profanity;

import java.util.List;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Data
@Controller
public class ProfanityFilter extends ProfanitiyLoader{

  private Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);
  private List<String> bannedWords;

  public ProfanityFilter() {
    bannedWords = this.loadBannedWordsFromXML();
  }

  public boolean isProfanityFree(String message) {

    boolean messageClean = true;
    boolean test;

    for (String badWord : bannedWords) {
      test = message.contains(badWord);

      if (test) {
        messageClean = false;
        break;
      }

    }
    return messageClean;
  }


}
