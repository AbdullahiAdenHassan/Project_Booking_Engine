package Hotel;

import org.codehaus.plexus.components.io.resources.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLToJava {
    private String file;

    public XMLToJava(){
        this.file = "C:\\My Java development\\Project_Booking_Engine\\src\\java\\Hotel\\Hotel.xml";
    }

    public void fileToXML(){
        File xmlFile = new File(file);

        try {
            JAXBContext java_context = JAXBContext.newInstance(Hotel.class);
            Unmarshaller unmarshaller = java_context.createUnmarshaller();

            Hotel hotel = (Hotel) unmarshaller.unmarshal(xmlFile);
            System.out.println(hotel);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
