package billstein.harald.chatApi.persistent;

import billstein.harald.chatApi.entity.MessageEntity;
import billstein.harald.chatApi.entity.UserEntity;
import billstein.harald.chatApi.utility.PasswordUtil;
import billstein.harald.chatApi.utility.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class PopulateDBMockData implements ApplicationListener<ContextRefreshedEvent> {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;

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
    harald.setHaschedPassword(PasswordUtil.createHashedPassword("badPassword", harald.getSalt()));
    harald.setToken(TokenUtil.generateToken());
    harald.setAdmin(true);
    harald.setWhenTokenWasCreated(TokenUtil.generateTime());

    lisette.setUserName("Lisette");
    lisette.setSalt(RandomStringUtils.randomAlphabetic(10));
    lisette.setHaschedPassword(PasswordUtil.createHashedPassword("realBadPass", harald.getSalt()));
    lisette.setToken(TokenUtil.generateToken());
    lisette.setAdmin(false);
    lisette.setWhenTokenWasCreated(TokenUtil.generateTime());

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
