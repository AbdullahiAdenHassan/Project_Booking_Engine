package Hotel;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Guest {
    private StringBuilder m_names; // maybe later!!!
    private String m_name;

    public Guest(){

    }

    public void setName(String name){

        this.m_name = name;
    }

    public String getName(){
        return m_name;
    }

}
