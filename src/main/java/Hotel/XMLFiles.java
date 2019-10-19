package Hotel;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLFiles {

    public void toXML(){
        try {
            File file = new File("C:\\My Java development\\Project_Booking_Engine\\src\\java\\Hotel\\Hotel.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            System.out.println("Root element :"+document.getDocumentElement().getNodeName());
            System.out.println("Attribute element(room number) :"+document.getDocumentElement().getAttribute("101"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
