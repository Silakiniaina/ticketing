package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaneSeat {
    
    private Plane plane;
    private TypeSeat typeSeat;
    private int quantity;
}