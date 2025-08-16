package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightPromotionArg {
    
    private int flightId;
    private int typeSeatId;
    private int seatNumber;
    private double percentage;
}
