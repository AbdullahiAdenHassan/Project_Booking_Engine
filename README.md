# Project_Booking_Engine
Building a booking engine that will include all functionality of a hotel services.

### Requirements
1. List Rooms.
2. Book a room (a specific date (dates must be legal)).
3. List Booked rooms (room nr, date, guest).
4. You can not book the same room twice on the same date.
5. If input is illegal you should be prompted with the menu again.
6. You can not book a room in the past.
-----
write test for invalid bookings
OBS! for same room

given booking for 10-12    adding a booking 11-13 fails
given booking for 10-12    adding a booking 8-11 fails
given booking for 10-12    adding a booking 8-14 fails
given booking for 10-14    adding a booking 11-12 fails
