package billstein.harald.chatApi.profanity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProfanitiyLoader {

  public List<String> loadBannedWordsFromXML() {

    Resource resource = new ClassPathResource("profanity.xml");
    List<String> bannedWords = new ArrayList<>();

    try {
      File xmlFile = resource.getFile();
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder;

      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);

      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("word");

      for (int i = 0; i < nodeList.getLength(); ++i) {
        bannedWords.add(nodeList.item(i).getTextContent());
      }

    } catch (IOException | ParserConfigurationException | SAXException e) {
      e.printStackTrace();
    }
    return bannedWords;

  }

}
