package billstein.harald.chatApi.service;

import billstein.harald.chatApi.Entity.MessageEntity;
import billstein.harald.chatApi.Entity.UserEntity;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.utility.MessageHandler;
import billstein.harald.chatApi.utility.PasswordHandler;
import billstein.harald.chatApi.utility.UserHandler;
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

    HttpStatus httpStatus;
    UserEntity user = null;
    boolean isUserPreset;
    boolean isPasswordCorrect = false;
    boolean isMessageFromProfanityFree = false;
    boolean isMessageSizeAccepted = false;

    isUserPreset = userHandler.isValidUser(messageReceived.getUser());
    logger.info("user Present: " + isUserPreset);
    if (isUserPreset) {
      user = userHandler.getUser(messageReceived.getUser());

      try {
        isPasswordCorrect = userHandler.userHasAccess(
            PasswordHandler.getPossibleMatches(messageReceived.getPassword(), user.getSalt()),
            user.getToken());
        logger.info("user password accepted: " + isPasswordCorrect);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }

    }

    if (isPasswordCorrect) {
      isMessageFromProfanityFree = messageHandler.isFreeFromProfanity(messageReceived.getMessage());
      logger.info("user message profanityfree: " + isMessageFromProfanityFree);
    }

    if (isMessageFromProfanityFree) {
      isMessageSizeAccepted = messageHandler.isMessageSizeAccepted(messageReceived.getMessage());
      logger.info("user message size OK?: " + isMessageSizeAccepted);
    }

    if (isMessageSizeAccepted) {
      MessageEntity messageEntity = new MessageEntity();
      messageEntity.setMessageContent(messageReceived.getMessage());
      messageEntity.setUser(user);
      messageHandler.saveMessage(messageEntity);
      httpStatus = HttpStatus.OK;
    } else {
      // TODO add more Httpstatus for diffretn errors
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
    }

    OutgoingMessage outgoingMessage = new OutgoingMessage();
    outgoingMessage.setUser(messageReceived.getUser());
    outgoingMessage.setMessage(messageReceived.getMessage());
    return ResponseEntity.status(httpStatus).body(outgoingMessage);
  }
}
