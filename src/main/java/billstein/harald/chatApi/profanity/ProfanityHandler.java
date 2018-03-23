package billstein.harald.chatApi.profanity;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class ProfanityHandler {

  private Logger logger = LoggerFactory.getLogger(ProfanityHandler.class);
  private ProfanityIO profanityIO;
  private List<String> bannedWords;

  public ProfanityHandler(ProfanityIO profanityIO) {
    this.profanityIO = profanityIO;
    bannedWords = profanityIO.loadBannedWordsFromXML();
  }

  public boolean isProfanityFree(String message) {

    for (String badWord : bannedWords) {
      if (message.contains(badWord)) {
        return false;
      }
    }
    return true;
  }

  public boolean reloadProfanityList() {

    List<String> tempList = profanityIO.loadBannedWordsFromXML();

    if (tempList.size() <= 0 || tempList.size() == this.bannedWords.size()) {
      return false;
    } else {
      this.bannedWords = tempList;
      return true;
    }
  }
  public boolean addWordToProfanityList(String word) {
    logger.info("P-handler: " + word);
    return profanityIO.addWordToXMLFile(word);
  }

  public boolean removeWordFromProfanityList(String word) {
    return profanityIO.deleteWordFromXMLFile(word);
  }
}
