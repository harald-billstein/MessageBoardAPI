package billstein.harald.chatApi.Entity;

import billstein.harald.chatApi.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer messageID;
  private String messageContent;

  @ManyToOne()
  @JoinColumn(name = "userID")
  @JsonBackReference
  private UserEntity user;

  public MessageEntity() {
  }

  public Integer getMessageID() {
    return messageID;
  }

  public void setMessageID(Integer messageID) {
    this.messageID = messageID;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
