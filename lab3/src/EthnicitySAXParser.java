import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EthnicitySAXParser extends DefaultHandler {
    private Set<String> ethnicities = new HashSet<>();
    private boolean inEthnicity = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("ethcty".equals(qName)) {
            inEthnicity = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inEthnicity) {
            String ethnicity = new String(ch, start, length).trim();
            if (!ethnicity.isEmpty()) {
                // Перетворення на одну стандартну форму
                ethnicity = standardizeEthnicity(ethnicity);
                ethnicities.add(ethnicity);
            }
            inEthnicity = false;
        }
    }

    private String standardizeEthnicity(String ethnicity) {
        // Перевірка на різні варіанти запису і перетворення на стандартний формат
        if (ethnicity.equalsIgnoreCase("ASIAN AND PACI")) {
            return "ASIAN AND PACIFIC ISLANDER";
        } else if (ethnicity.equalsIgnoreCase("WHITE NON HISPANIC")) {
            return "WHITE NON HISP";
        } else if (ethnicity.equalsIgnoreCase("BLACK NON HISP")) {
            return "BLACK NON HISPANIC";
        }
        // Якщо немає відповідності, просто повертаємо оригінальне значення
        return ethnicity;
    }


    @Override
    public void endDocument() throws SAXException {
        System.out.println("Всі національональні групи в документі:");
        ethnicities.forEach(System.out::println);
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("Popular_Baby_Names_NY.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            EthnicitySAXParser handler = new EthnicitySAXParser();
            saxParser.parse(inputFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
