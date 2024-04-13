import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class TagsSAXParser {

    public static void main(String[] args) {
        String xmlFilePath = "Popular_Baby_Names_NY.xml";

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            saxParser.parse(new File(xmlFilePath), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class XMLHandler extends DefaultHandler {
        private StringBuilder content;
        private Set<String> tags;

        public XMLHandler() {
            content = new StringBuilder();
            tags = new HashSet<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            content.setLength(0);
            tags.add(qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            content.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (!content.toString().trim().isEmpty()) {
                System.out.println("Content of <" + qName + ">: " + content);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("List of all tags:");
            for (String tag : tags) {
                System.out.println(tag);
            }
        }
    }
}
