package dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BookingPassengerArg {
    
    private int bookingId;
    private int typeSeatId; 
    private int seatNumber;
}
