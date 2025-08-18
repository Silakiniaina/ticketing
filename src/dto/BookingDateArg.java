package dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BookingDateArg {
    
    private int flightId;
    private String bookingDate;
}
