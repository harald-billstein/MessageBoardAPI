package billstein.harald.chatApi.repository;

import billstein.harald.chatApi.Entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

}