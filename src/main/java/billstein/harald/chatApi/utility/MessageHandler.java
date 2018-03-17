package billstein.harald.chatApi.utility;

import billstein.harald.chatApi.Entity.MessageEntity;
import billstein.harald.chatApi.model.OutgoingMessage;
import billstein.harald.chatApi.model.ProfanityFilter;
import billstein.harald.chatApi.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class MessageHandler {

  private static final int MAX_LENGTH_MESSAGE = 5;
  private ProfanityFilter profanityFilter;
  private MessageRepository messageRepository;

  public MessageHandler(ProfanityFilter profanityFilter, MessageRepository messageRepository) {
    this.profanityFilter = profanityFilter;
    this.messageRepository = messageRepository;
  }

  public boolean isFreeFromProfanity(String message) {
    return profanityFilter.isProfanityFree(message);
  }

  public void saveMessage(MessageEntity messageEntity) {
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
    if (message.length() > MAX_LENGTH_MESSAGE) {
      return false;
    } else {
      return true;
    }
  }
}
