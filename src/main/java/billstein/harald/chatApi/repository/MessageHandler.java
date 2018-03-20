package billstein.harald.chatApi.repository;

import billstein.harald.chatApi.database.MessageRepository;
import billstein.harald.chatApi.entity.MessageEntity;
import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.profanity.ProfanityFilter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class MessageHandler {

  private static final int MAX_LENGTH_MESSAGE = 255;
  private ProfanityFilter profanityFilter;
  private MessageRepository messageRepository;

  public MessageHandler(ProfanityFilter profanityFilter, MessageRepository messageRepository) {
    this.profanityFilter = profanityFilter;
    this.messageRepository = messageRepository;
  }

  public boolean isFreeFromProfanity(String message) {
    return profanityFilter.isProfanityFree(message);
  }

  public void saveIncomingMessage(String message, UserEntity user) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setMessageContent(message);
    messageEntity.setUser(user);

    messageRepository.save(messageEntity);
  }

  public List<OutgoingMessage> getLatestMessages() {
    Iterable<MessageEntity> allMessages = messageRepository.findAll();

    List<OutgoingMessage> latestMessages = new ArrayList<>();

    for (MessageEntity message : allMessages) {
      OutgoingMessage msg = new OutgoingMessage();
      msg.setUser(message.getUser().getUserName());
      msg.setMessage(message.getMessageContent());
      latestMessages.add(msg);
    }

    return latestMessages;

  }

  public boolean isMessageSizeAccepted(String message) {
    return message.length() <= MAX_LENGTH_MESSAGE;
  }

  public OutgoingMessage createOutgoingMessage(IncomingMessage incomingMessage) {
    OutgoingMessage outgoingMessage = new OutgoingMessage();
    outgoingMessage.setMessage(incomingMessage.getMessage());
    outgoingMessage.setUser(incomingMessage.getUser());

    return outgoingMessage;
  }
}
