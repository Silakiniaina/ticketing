package model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking {
    
    private int id;
    private Timestamp bookingDatetime;
    private User user;
    private Flight flight;
}
