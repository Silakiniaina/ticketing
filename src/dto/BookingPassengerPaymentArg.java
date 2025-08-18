package dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BookingPassengerPaymentArg {
    
    private int bookingId;
    private String paymentDate;
}
