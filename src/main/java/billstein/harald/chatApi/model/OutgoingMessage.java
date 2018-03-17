package billstein.harald.chatApi.model;

import lombok.Data;

@Data
public class OutgoingMessage {

  private String user;
  private String message;
}
