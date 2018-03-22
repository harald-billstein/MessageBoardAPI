package billstein.harald.chatApi.service;

import billstein.harald.chatApi.profanity.ProfanityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v2")
public class AdminServiceController {

  private Logger logger = LoggerFactory.getLogger(AdminServiceController.class);
  private ProfanityHandler profanityHandler = new ProfanityHandler();

  @PostMapping(path = "/add/banned/word")
  public ResponseEntity<String> addBannedWord(@RequestParam String word) {
    logger.info("Word going on the list: " + word);
    profanityHandler.addWordToProfanityList(word);

    return ResponseEntity.ok(word);
  }

}
