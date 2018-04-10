package billstein.harald.chatApi.persistent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ProfanityIO {

  private static final String URL = "profanity.xml";
  private final Logger logger = LoggerFactory.getLogger(ProfanityIO.class);
  private XMLOutputter xmlOutputter;
  private SAXBuilder saxBuilder;

  public ProfanityIO() {
    logger.info("ProfanityIO - created");
    init();
  }

  private void init() {
    xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
    saxBuilder = new SAXBuilder();

    boolean restoreFileNeeded = false;

    try {
      new FileInputStream(URL);
    } catch (IOException e) {
      restoreFileNeeded = true;
      logger.warn("File missing.....Restoring");
    }

    if (restoreFileNeeded) {
      createFile();
    }
  }


  public List<String> loadBannedWordsFromXML() {
    List<String> bannedWords = new ArrayList<>();
    try {
      Document document = getDocument();
      List<Element> children = getChildrenElements(document);

      for (Element child : children) {
        bannedWords.add(child.getText());
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
      save(document);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean addWordToXMLFile(String word) {

    Document document;
    boolean success = false;
    boolean duplicateFound = false;

    try {
      document = getDocument();
      Element root = document.getRootElement();
      List<Element> children = getChildrenElements(document);
      Element wordToBeInserted = new Element("word");

      for (Element child : children) {
        if (child.getText().equals(word)) {
          duplicateFound = true;
          break;
        }
      }

      if (!duplicateFound) {
        wordToBeInserted.addContent(new Text(word));
        root.addContent(wordToBeInserted);
        save(document);
        success = true;
      }


    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  public boolean deleteWordFromXMLFile(String word) {
    Document document;
    boolean success = false;

    try {
      document = getDocument();
      List<Element> children = getChildrenElements(document);

      for (Element aChildren : children) {
        if (word.equals(aChildren.getText())) {
          aChildren.detach();
          break;
        }

      }

      save(document);

      success = true;

    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  private Document getDocument() throws JDOMException, IOException {
    return saxBuilder.build(URL);
  }

  private List<Element> getChildrenElements(Document document) {
    Element root = document.getRootElement();
    return root.getChildren();
  }

  private void save(Document doc) throws IOException {
    xmlOutputter.output(doc, new FileOutputStream(URL));
  }

  public boolean editWordFromXMLFile(String oldWord, String newWord) {
    boolean success = false;

    try {
      Document document = getDocument();
      List<Element> children = getChildrenElements(document);

      for (Element aChildren : children) {
        if (aChildren.getText().equals(oldWord)) {
          aChildren.setText(newWord);
          success = true;
          break;
        }
      }
    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }

    return success;
  }
}
