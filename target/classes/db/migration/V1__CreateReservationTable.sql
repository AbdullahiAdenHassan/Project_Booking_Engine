CREATE TABLE IF NOT EXISTS reservation (
    reservation_id UUID PRIMARY KEY NOT NULL,
    hotel_brand VARCHAR (150) NOT NULL,
    hotel_room INT NOT NULL,
    first_name VARCHAR (150) NOT NULL,
    last_name VARCHAR (150) NOT NULL,
    arrival_date DATE NOT NULL,
    departure_date DATE NOT NULL
);
