package Hotel;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "hotel_rooms")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Hotel implements Serializable {
    private static final long serialVersionUTD = 1L;
    private String roomNumber;
    private String id;
    private String date;
    private String name;
    private String description;

    public Hotel() {
        super();
    }

    //Setters and Getters
    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    // It is called immediately after the object is created and before the unmarshalling begins.
    // The callback provides an opportunity to initialize JavaBean properties prior to unmarshalling.
    void beforeUnmarshal(Unmarshaller unmarshaller, Object parent) {
        System.out.println("Before Unmarshaller Callback");
    }
    // It is called after all the properties are unmarshalled for this object,

    // but before this object is set to the parent object.
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        System.out.println("After Unmarshaller Callback");
    }
}
