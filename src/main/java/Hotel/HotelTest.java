package Hotel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HotelTest {
    private int id;
    private String name;
    private String address;
    private String email;

    private int getId(){
        return id;
    }

    @XmlAttribute
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    @XmlElement
    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }
    @XmlElement
    public void setAddress(String address){
        this.address = address;
    }

    public String getEmail(){
        return email;
    }

    @XmlElement
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString(){
        return "Hotel test [id:"+id+", name:"+name+", email:"+email+", address:"+ address +"]";
    }

}
