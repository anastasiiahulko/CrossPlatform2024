package app;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PopularNamesParser {

    public static void main(String[] args) {
        String xmlFilePath = "Popular_Baby_Names_NY.xml";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть етнічну групу (напр. HISPANIC, WHITE NON HISPANIC, ASIAN AND PACIFIC ISLANDER, BLACK NON HISPANIC):");
        String targetEthnicity = scanner.nextLine();

        try {
            List<NameData> namesData = parseXML(xmlFilePath, targetEthnicity);
            if (namesData.isEmpty()) {
                System.out.println("Попередження: Для введеної етнічної групи немає даних.");
            } else {
                Collections.sort(namesData);
                int numberOfNames = Math.min(10, namesData.size());
                List<NameData> topNames = namesData.subList(0, numberOfNames);
                saveToXML(topNames, "Sorted_Popular_Names.xml");
                System.out.println("Дані успішно збережено!");

                readAndPrintXML("Sorted_Popular_Names.xml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<NameData> parseXML(String xmlFilePath, String targetEthnicity)
            throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        List<NameData> namesData = new ArrayList<>();
        Set<String> uniqueNames = new HashSet<>(); // Зберігає унікальні імена

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlFilePath));
        doc.getDocumentElement().normalize();

        NodeList rowList = doc.getElementsByTagName("row");
        for (int i = 0; i < rowList.getLength(); i++) {
            Node rowNode = rowList.item(i);
            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rowElement = (Element) rowNode;
                String ethnicity = rowElement.getElementsByTagName("ethcty").item(0).getTextContent();
                if (ethnicity.equals(targetEthnicity)) {
                    String name = rowElement.getElementsByTagName("nm").item(0).getTextContent().toUpperCase(); // Перевести у верхній регістр
                    int count = Integer.parseInt(rowElement.getElementsByTagName("cnt").item(0).getTextContent());
                    int rank = Integer.parseInt(rowElement.getElementsByTagName("rnk").item(0).getTextContent());
                    if (!uniqueNames.contains(name)) { // Перевіряємо, чи ім'я ще не збережено
                        namesData.add(new NameData(name, count, rank));
                        uniqueNames.add(name); // Додаємо ім'я до множини унікальних імен
                    }
                }
            }
        }

        return namesData;
    }

    public static void saveToXML(List<NameData> namesData, String xmlFilePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("PopularNames");
        doc.appendChild(rootElement);

        for (NameData nameData : namesData) {
            Element nameElement = doc.createElement("Name");
            rootElement.appendChild(nameElement);

            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(nameData.getName()));
            nameElement.appendChild(name);

            Element count = doc.createElement("Count");
            count.appendChild(doc.createTextNode(String.valueOf(nameData.getCount())));
            nameElement.appendChild(count);

            Element rank = doc.createElement("Rank");
            rank.appendChild(doc.createTextNode(String.valueOf(nameData.getRank())));
            nameElement.appendChild(rank);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    public static void readAndPrintXML(String xmlFilePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlFilePath));
        doc.getDocumentElement().normalize();

        NodeList nameList = doc.getElementsByTagName("Name");
        for (int i = 0; i < nameList.getLength(); i++) {
            Node nameNode = nameList.item(i);
            if (nameNode.getNodeType() == Node.ELEMENT_NODE) {
                Element nameElement = (Element) nameNode;
                String name = getTextContent(nameElement, "Name");
                String count = getTextContent(nameElement, "Count");
                String rank = getTextContent(nameElement, "Rank");
                if (!name.equals("N/A") && !count.equals("N/A") && !rank.equals("N/A")) {
                    System.out.println("Ім'я: " + name + ", Підрахунок: " + count + ", Ранг: " + rank);
                }
            }
        }
    }

    private static String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                return node.getTextContent();
            }
        }
        return "N/A";
    }
}
