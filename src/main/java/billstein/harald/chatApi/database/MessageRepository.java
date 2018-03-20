package billstein.harald.chatApi.database;

import billstein.harald.chatApi.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

}