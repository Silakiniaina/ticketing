package model;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plane {
    
    private int id;
    private String model; 
    private Date fabricationDate;
    private List<PlaneSeat> seats;

    /* -------------------------------------------------------------------------- */
    /*                                  Functions                                 */
    /* -------------------------------------------------------------------------- */
    public void addSeat(PlaneSeat p){
        this.getSeats().add(p);
    }
}
