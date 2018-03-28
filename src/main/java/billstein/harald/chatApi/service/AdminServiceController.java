package billstein.harald.chatApi.service;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.model.IncomingProfanityRequest;
import billstein.harald.chatApi.handlers.ProfanityHandler;
import billstein.harald.chatApi.model.OutgoingProfanityRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v2")
public class AdminServiceController {

  private Logger logger = LoggerFactory.getLogger(AdminServiceController.class);
  private ProfanityHandler profanityHandler;
  private UserHandler userHandler;

  public AdminServiceController(ProfanityHandler profanityHandler, UserHandler userHandler) {
    logger.info("AdminServiceController");
    this.profanityHandler = profanityHandler;
    this.userHandler = userHandler;
  }

  @PostMapping(path = "/add/banned/word")
  public ResponseEntity<OutgoingProfanityRequest> addBannedWord(
      @RequestBody IncomingProfanityRequest profanityReq) {

    boolean success = false;
    OutgoingProfanityRequest outgoingProfanityRequest;

    UserEntity userEntity = userHandler.getUser(profanityReq.getUserName());
    boolean tokenIsValid = userHandler.tokenIsValied(profanityReq.getToken(), userEntity);

    if (tokenIsValid && userEntity.isAdmin()) {
      success = profanityHandler.addWordToProfanityList(profanityReq.getWord());
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest("Word added to list", true);
    } else {
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest("Something was not meant for the list", false);
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

    if (tokenIsValid && userEntity.isAdmin()) {
      success = profanityHandler.removeWordFromProfanityList(profanityReq.getWord());
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest("Word removed to list", true);
    } else {
      outgoingProfanityRequest = profanityHandler
          .getOutGoingProfanityRequest("Something was meant for this list", false);
    }

    if (success) {
      return ResponseEntity.ok(outgoingProfanityRequest);
    } else {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(outgoingProfanityRequest);
    }
  }
}
