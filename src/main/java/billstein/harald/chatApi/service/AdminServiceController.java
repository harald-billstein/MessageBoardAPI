package billstein.harald.chatApi.service;

import billstein.harald.chatApi.profanity.ProfanityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    boolean success = profanityHandler.addWordToProfanityList(word);

    if (success) {
      return ResponseEntity.ok(word);
    } else {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
  }

  @DeleteMapping(path = "/remove/banned/word")
  public ResponseEntity<String> removeBannedWord(@RequestParam String word) {
    logger.info("Word going to be deleted from the list: " + word);
    boolean success = profanityHandler.removeWordFromProfanityList(word);

    if (success) {
      return ResponseEntity.ok(word);
    } else {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
  }
}
