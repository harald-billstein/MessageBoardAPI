package billstein.harald.chatApi.service;

import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.repository.MessageHandler;
import billstein.harald.chatApi.utility.PasswordUtil;
import billstein.harald.chatApi.repository.UserHandler;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(path = "/api")
public class MessageServiceController {

  private Logger logger = LoggerFactory.getLogger(MessageServiceController.class);

  private MessageHandler messageHandler;
  private UserHandler userHandler;

  public MessageServiceController(MessageHandler messageHandler,
      UserHandler userHandler) {
    this.messageHandler = messageHandler;
    this.userHandler = userHandler;
  }

  @GetMapping(path = "/latest/messages")
  public ResponseEntity<List<OutgoingMessage>> getLatestMessages() {
    logger.info("/all/messages");

    List<OutgoingMessage> latestMessages = messageHandler.getLatestMessages();

    if (latestMessages.size() > 0) {
      return ResponseEntity.ok().body(latestMessages);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping(path = "/send/message")
  public ResponseEntity<OutgoingMessage>
  sendMessage(@RequestBody() IncomingMessage messageReceived) {
    logger.info("send/message");

    UserEntity user;
    boolean isUserPreset;
    boolean isPasswordCorrect = false;
    boolean isMessageFromProfanityFree;
    boolean isMessageSizeAccepted;

    OutgoingMessage outgoingMessage = messageHandler.createOutgoingMessage(messageReceived);

    isUserPreset = userHandler.isValidUser(messageReceived.getUser());

    if (isUserPreset) {
      user = userHandler.getUser(messageReceived.getUser());

      try {
        isPasswordCorrect = userHandler.userHasAccess(
            PasswordUtil.getPossibleMatches(messageReceived.getPassword(), user.getSalt()),
            user.getToken());
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }

    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outgoingMessage);
    }

    if (isPasswordCorrect) {
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
