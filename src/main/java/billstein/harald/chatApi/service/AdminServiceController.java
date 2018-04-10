package billstein.harald.chatApi.service;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.ProfanityHandler;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.language.English;
import billstein.harald.chatApi.language.Language;
import billstein.harald.chatApi.model.IncomingProfanityRequest;
import billstein.harald.chatApi.model.OutgoingProfanityRequest;
import billstein.harald.chatApi.utility.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v2")
public class AdminServiceController {

  private Logger logger = LoggerFactory.getLogger(AdminServiceController.class);
  private ProfanityHandler profanityHandler;
  private UserHandler userHandler;
  private Language english = new English();

  public AdminServiceController(ProfanityHandler profanityHandler, UserHandler userHandler) {
    logger.info("AdminServiceController");
    this.profanityHandler = profanityHandler;
    this.userHandler = userHandler;
  }

  @PutMapping(path = "/add/banned/word")
  public ResponseEntity<OutgoingProfanityRequest> addBannedWord(
      @RequestBody IncomingProfanityRequest profanityReq) {

    boolean success = false;
    OutgoingProfanityRequest outgoingProfanityRequest;

    UserEntity userEntity = userHandler.getUser(profanityReq.getUserName());
    boolean tokenIsValid = userHandler.tokenIsValied(profanityReq.getToken(), userEntity);
    boolean tokenExpired = TokenUtil.hasTokenExpired(userEntity);

    if (tokenIsValid && userEntity.isAdmin() && !tokenExpired) {
      success = profanityHandler.addWordToProfanityList(profanityReq.getWord());
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest(english.getProfanityWordAddedText(), true);
    } else {
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest(english.getProfanityWordWasNotAddedText(), false);
    }

    if (success) {
      return ResponseEntity.ok(outgoingProfanityRequest);
    } else {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(outgoingProfanityRequest);
    }
  }

  @DeleteMapping(path = "/remove/banned/word")
  public ResponseEntity<OutgoingProfanityRequest> removeBannedWord(
      @RequestBody IncomingProfanityRequest profanityReq) {

    boolean success = false;
    OutgoingProfanityRequest outgoingProfanityRequest;

    UserEntity userEntity = userHandler.getUser(profanityReq.getUserName());
    boolean tokenIsValid = userHandler.tokenIsValied(profanityReq.getToken(), userEntity);
    boolean tokenExpired = TokenUtil.hasTokenExpired(userEntity);

    if (tokenIsValid && userEntity.isAdmin() && !tokenExpired) {
      success = profanityHandler.removeWordFromProfanityList(profanityReq.getWord());
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest(english.getProfanityWordWasRemovedText(), true);
    } else {
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest(english.getProfanityWordWasNotRemovedText(), false);
    }

    if (success) {
      return ResponseEntity.ok(outgoingProfanityRequest);
    } else {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(outgoingProfanityRequest);
    }
  }
}
