package billstein.harald.chatApi.profanity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class ProfanityIO {

  private static final String URL = "src/main/resources/profanity.xml";
  private static final String URL2 = new ClassPathResource("profanity.xml").getPath();
  private Logger logger = LoggerFactory.getLogger(ProfanityIO.class);
  private XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
  private SAXBuilder saxBuilder = new SAXBuilder();

  public ProfanityIO() {
    init();
  }

  private void init() {
    File file = new File(URL);

    if (!file.exists()) {
      createFile();
    }

    loadBannedWordsFromXML();
  }


  List<String> loadBannedWordsFromXML() {
    List<String> bannedWords = new ArrayList<>();
    try {
      Document doc = saxBuilder.build("src/main/resources/profanity.xml");
      List<Element> elements = doc.getRootElement().getChildren();

      for (Element element : elements) {
        logger.info("" + element.getText());
      }

    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return bannedWords;
  }


  private void createFile() {
    Document document = new Document();

    Element rootElement = new Element("bannedwords");
    document.setRootElement(rootElement);

    Element wordDamn = new Element("word");
    wordDamn.addContent(new Text("damn"));

    Element wordFuck = new Element("word");
    wordFuck.addContent(new Text("fuck"));

    Element wordCunt = new Element("word");
    wordCunt.addContent(new Text("cunt"));

    rootElement.addContent(wordDamn);
    rootElement.addContent(wordFuck);
    rootElement.addContent(wordCunt);

    try {
      xmlOutputter.output(document, new FileOutputStream(URL));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  boolean addWordToXMLFile(String word) {

    Document doc;
    boolean success = false;

    try {
      doc = saxBuilder.build("src/main/resources/profanity.xml");
      Element root = doc.getRootElement();
      Element wordToBeInserted = new Element("word");
      wordToBeInserted.addContent(new Text(word));
      root.addContent(wordToBeInserted);
      xmlOutputter.output(doc, new FileOutputStream("src/main/resources/profanity.xml"));
      success = true;
    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  public boolean deleteWordFromXMLFile(String word) {
    // TODO when one word left <bannedwords> get messedup
    logger.info(word);
    Document doc;
    boolean success = false;

    try {
      doc = saxBuilder.build("src/main/resources/profanity.xml");
      Element root = doc.getRootElement();

      for (int i = 0; i < root.getChildren().size(); i++) {
        if (root.getChildren().get(i).getText().equals(word)) {
          root.getChildren().remove(i);
        }
      }

      xmlOutputter.output(doc, new FileOutputStream("src/main/resources/profanity.xml"));

      success = true;

    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  public boolean editWordFromXMLFile() {

    new RuntimeException("editeWordFromXMLFile not implemented");
    return false;
  }
}
