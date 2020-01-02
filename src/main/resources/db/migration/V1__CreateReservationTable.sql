CREATE TABLE IF NOT EXISTS reservation (
    reservation_id UUID PRIMARY KEY NOT NULL,
    hotel_chain VARCHAR (150) NOT NULL CHECK (
    hotel_chain = 'Comfort_Hotel' OR
    hotel_chain = 'COMFORT_HOTEL' OR
    hotel_chain = 'comfort_hotel' OR
    hotel_chain = 'Quality_Hotel' OR
    hotel_chain = 'quality_hotel' OR
    hotel_chain = 'QUALITY_HOTEL'
    ),
    hotel_room INT NOT NULL,
    first_name VARCHAR (150) NOT NULL,
    last_name VARCHAR (150) NOT NULL,
    arrival_date DATE NOT NULL,
    departure_date DATE NOT NULL
);
