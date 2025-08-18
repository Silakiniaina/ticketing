SELECT fsp.price, fsp.seat_number,
    (SELECT COUNT(*) FROM booking_passenger bp
    JOIN booking b ON bp.booking_id = b.id
    WHERE b.flight_id = 11 AND bp.type_seat_id = 1 AND promotion_date <= '18-08-2025') AS booked_seats
    FROM flight_seat_promotion fsp
    WHERE fsp.flight_id = 1 AND fsp.type_seat_id = 1 ;
    