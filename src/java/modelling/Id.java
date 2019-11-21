package modelling;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

public class Id {
    //use Enum later!
    private final int id;
    private Id(final int id){
        isTrue(id > 0);
        this.id = id;
    }
    public static Id of(final int Id){
        return new Id(Id);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
