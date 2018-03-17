package billstein.harald.chatApi.repository;

import billstein.harald.chatApi.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
