package billstein.harald.chatApi.repository;

import billstein.harald.chatApi.Entity.MessageEntity;
import billstein.harald.chatApi.Entity.UserEntity;
import billstein.harald.chatApi.utility.PasswordHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class PopulateDBMockData implements ApplicationListener<ContextRefreshedEvent> {

  private MessageRepository messageRepository;
  private UserRepository userRepository;

  public PopulateDBMockData(MessageRepository messageRepository, UserRepository userRepository) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    initData();
  }

  private void initData() {

    UserEntity harald = new UserEntity();
    UserEntity lisette = new UserEntity();

    harald.setUserName("Harald");
    harald.setSalt(RandomStringUtils.randomAlphabetic(10));
    harald.setToken(PasswordHandler.createHashedPassword("badPassword", harald.getSalt()));

    lisette.setUserName("Lisette");
    lisette.setSalt(RandomStringUtils.randomAlphabetic(10));
    lisette.setToken(PasswordHandler.createHashedPassword("realBadPass", harald.getSalt()));

    userRepository.save(harald);
    userRepository.save(lisette);

    MessageEntity messageFromHarald1 = new MessageEntity();
    messageFromHarald1.setMessageContent("Anyone around?");
    messageFromHarald1.setUser(harald);
    messageRepository.save(messageFromHarald1);

    MessageEntity messageFromLisette1 = new MessageEntity();
    messageFromLisette1.setUser(lisette);
    messageFromLisette1.setMessageContent("Yeah, sup?");
    messageRepository.save(messageFromLisette1);

    MessageEntity messageFromHarald2 = new MessageEntity();
    messageFromHarald2.setMessageContent("Cool, need some help!");
    messageFromHarald2.setUser(harald);
    messageRepository.save(messageFromHarald2);

    MessageEntity messageFromLisette2 = new MessageEntity();
    messageFromLisette2.setMessageContent("What can i do for you?");
    messageFromLisette2.setUser(lisette);
    messageRepository.save(messageFromLisette2);
  }

}
