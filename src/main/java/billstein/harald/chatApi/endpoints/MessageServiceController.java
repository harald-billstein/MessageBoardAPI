package billstein.harald.chatApi.endpoints;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.handlers.MessageHandler;
import billstein.harald.chatApi.handlers.UserHandler;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.utility.TokenUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v2/messages")
public class MessageServiceController {

  private final Logger logger = LoggerFactory.getLogger(MessageServiceController.class);

  private final MessageHandler messageHandler;
  private final UserHandler userHandler;

  public MessageServiceController(MessageHandler messageHandler,
      UserHandler userHandler) {
    this.messageHandler = messageHandler;
    this.userHandler = userHandler;
  }

  @GetMapping(path = "/retrieve")
  public ResponseEntity<List<OutgoingMessage>> getLatestMessages(@RequestParam String userName,
      @RequestParam String token) {
    logger.info("Latest messages retrieved");

    UserEntity userEntity = userHandler.getUser(userName);
    boolean tokenExpired = TokenUtil.hasTokenExpired(userEntity);
    boolean tokenValid = userEntity.getToken().equals(token);

    if (userEntity != null && tokenValid && !tokenExpired) {
      List<OutgoingMessage> latestMessages = messageHandler.getLatestMessages();
      return ResponseEntity.ok().body(latestMessages);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @PostMapping(path = "/message/create")
  public ResponseEntity<OutgoingMessage>
  sendMessage(@RequestBody() IncomingMessage messageReceived) {
    logger.info("Message sent");

    OutgoingMessage outgoingMessage = messageHandler.createOutgoingMessage(messageReceived);
    UserEntity user;
    boolean isUserPreset;
    boolean isPasswordCorrect;
    boolean isMessageFromProfanityFree;
    boolean isMessageSizeAccepted;
    boolean hasTokenExpired;

    isUserPreset = userHandler.isValidUser(messageReceived.getUser());
    if (isUserPreset) {
      user = userHandler.getUser(messageReceived.getUser());
      isPasswordCorrect = messageReceived.getToken().equals(user.getToken());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outgoingMessage);
    }

    if (isPasswordCorrect) {
      hasTokenExpired = TokenUtil.hasTokenExpired(user);

    } else {
      System.out.println("2");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outgoingMessage);
    }

    if (!hasTokenExpired) {
      isMessageFromProfanityFree = messageHandler.isFreeFromProfanity(messageReceived.getMessage());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outgoingMessage);
    }

    if (isMessageFromProfanityFree) {
      isMessageSizeAccepted = messageHandler.isMessageSizeAccepted(messageReceived.getMessage());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(outgoingMessage);
    }

    if (isMessageSizeAccepted) {
      messageHandler.saveIncomingMessage(messageReceived.getMessage(), user);
      return ResponseEntity.status(HttpStatus.OK).body(outgoingMessage);
    } else {
      return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(outgoingMessage);
    }
  }
}
