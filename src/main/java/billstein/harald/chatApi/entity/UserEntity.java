package billstein.harald.chatApi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userID;
  @Column(unique = true)
  private String userName;
  private String token;
  private String salt;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  @JsonManagedReference
  private Set<MessageEntity> message;

  public UserEntity() {
  }

  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public Set<MessageEntity> getMessage() {
    return message;
  }

  public void setMessage(Set<MessageEntity> message) {
    this.message = message;
  }
}
