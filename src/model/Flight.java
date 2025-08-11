package model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Flight {
    
    private int id;
    private Timestamp departureDatetime;
    private Timestamp arrivalDatetime;
    private City departureCity;
    private City arrivalCity;
    private Plane plane;

}
