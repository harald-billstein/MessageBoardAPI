package billstein.harald.chatApi.model;

import lombok.Data;

@Data
public class IncomingProfanityRequest {

  private String userName;
  private String token;
  private String word;

}
