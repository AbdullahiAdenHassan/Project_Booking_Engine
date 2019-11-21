package modelling;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Guest {
    private final String name;

    private Guest(final String fullname) {
        notNull(fullname);
        isTrue(fullname.matches("^[a-zA-Z]+"));
        this.name = fullname;
    }

    public static Guest of(final String fullname){
        return new Guest(fullname);
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(name, guest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return ""+ name ;
    }
}
