package billstein.harald.chatApi.persistent;

import billstein.harald.chatApi.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
