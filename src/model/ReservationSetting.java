package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSetting {
    
    private int hourLimitReserving;
    private int hourLimitCanceling;
}
