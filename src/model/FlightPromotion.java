package model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightPromotion {

    private Flight flight;
    private TypeSeat typeSeat;
    private int seatNumber;
    private double price;
    private Date promotionDate;
}