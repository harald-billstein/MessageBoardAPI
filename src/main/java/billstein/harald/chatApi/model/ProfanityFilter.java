package billstein.harald.chatApi.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Data
@Controller
public class ProfanityFilter {

  private static List<String> badWords;
  private Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);

  public ProfanityFilter() {
    loadBannedWordsFromXML();
  }

  public boolean isProfanityFree(String message) {

    boolean messageClean = true;
    boolean test;

    for (String badWord : badWords) {
      test = message.contains(badWord);

      if (test) {
        messageClean = false;
        break;
      }

    }
    return messageClean;
  }

  private void loadBannedWordsFromXML() {

    Resource resource = new ClassPathResource("profanity.xml");

    try {
      File xmlFile = resource.getFile();
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder;

      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);

      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("word");

      List<String> bannedWords = new ArrayList<>();
      for (int i = 0; i < nodeList.getLength(); ++i) {
        bannedWords.add(nodeList.item(i).getTextContent());
      }
      badWords = bannedWords;

    } catch (IOException | ParserConfigurationException | SAXException e) {
      e.printStackTrace();
    }

  }
}
