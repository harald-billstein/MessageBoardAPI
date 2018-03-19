package billstein.harald.chatApi.utility;

import billstein.harald.chatApi.Entity.MessageEntity;
import billstein.harald.chatApi.Entity.UserEntity;
import billstein.harald.chatApi.model.IncomingMessage;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.model.ProfanityFilter;
import billstein.harald.chatApi.repository.MessageRepository;
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
