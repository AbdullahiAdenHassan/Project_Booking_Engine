package Hotel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JAXBToXML {

    public JAXBToXML() {

    }

    public static void toMashaller(){
        String path = "C:\\My Java development\\Project_Booking_Engine\\src\\main\\java\\Hotel\\HotelTest.xml";
        System.out.println("JAXB Object to XML example");
        System.out.println();
        HotelTest hotelTest = new HotelTest();
        hotelTest.setAddress("London");
        hotelTest.setEmail("john@ex,com");
        hotelTest.setId(33);
        hotelTest.setName("John Doe");

        try {
            JAXBContext context = JAXBContext.newInstance(HotelTest.class);
            Marshaller marshaller = context.createMarshaller();

            /** output the XML in pretty format */
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            /** display the output in the console */
            marshaller.marshal(hotelTest,System.out);

            /** put the XML to the file - will be used by the unmarshal example */
            marshaller.marshal(hotelTest,new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}