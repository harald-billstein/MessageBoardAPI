package billstein.harald.chatApi.model;

import lombok.Data;

@Data
public class OutgoingProfanityRequest {

  private boolean success;
  private String message;
}
