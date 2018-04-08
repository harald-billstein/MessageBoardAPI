package billstein.harald.chatApi.language;

import org.junit.Assert;
import org.junit.Test;

public class LanguageTest {

  @Test
  public void englishTest() {
    Language language = new English();

    Assert.assertEquals(language.getProfanityWordAddedText(), "Word added to list");
    Assert.assertEquals(language.getProfanityWordWasNotAddedText(),
        "Something was not meant for the list");
    Assert.assertEquals(language.getProfanityWordWasRemovedText(), "Word removed to list");
    Assert.assertEquals(language.getProfanityWordWasNotRemovedText(),
        "Something was meant for this list");
  }

}
