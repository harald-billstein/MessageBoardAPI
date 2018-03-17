package billstein.harald.chatApi.model;

import lombok.Data;

@Data
public class IncomingMessage {

  private String user;
  private String password;
  private String message;

}
