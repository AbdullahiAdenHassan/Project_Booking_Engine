package com.booking.project.Modelling;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Brand {
    private final String Name;

    private Brand (final String name){
        notNull(name);
        isTrue(name.matches("^[a-zA-Z]+"));
        this.Name = name;
    }

    public static Brand of(final String name){
        return new Brand(name);
    }

    public String getBrandName() {
        return Name;
    }
}
