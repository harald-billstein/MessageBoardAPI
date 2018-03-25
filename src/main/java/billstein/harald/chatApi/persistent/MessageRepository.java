package billstein.harald.chatApi.persistent;

import billstein.harald.chatApi.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

}