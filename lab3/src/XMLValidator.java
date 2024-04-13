import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {

    public static void main(String[] args) {
        String xmlFilePath = "Popular_Baby_Names_NY.xml";
        String xsdFilePath = "Popular_Baby_Names_NY.xsd";


        boolean isValid = validateXMLSchema(xsdFilePath, xmlFilePath);

        if (isValid) {
            System.out.println("XML відповідає XSD схемі.");
        } else {
            System.out.println("XML не відповідає XSD схемі.");
        }
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
    }
}
