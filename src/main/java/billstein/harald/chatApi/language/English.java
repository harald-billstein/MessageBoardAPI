package billstein.harald.chatApi.language;

public class English implements Language {

  @Override
  public String getProfanityWordAddedText() {
    return "Word added to list";
  }

  @Override
  public String getProfanityWordWasNotAddedText() {
    return "Something was not meant for the list";
  }

  @Override
  public String getProfanityWordWasRemovedText() {
    return "Word removed to list";
  }

  @Override
  public String getProfanityWordWasNotRemovedText() {
    return "Something was meant for this list";
  }
}
