package model;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BookingPassenger {
    
    private int id; 
    private Booking booking; 
    private TypeSeat typeSeat; 
    private double price;
    private double promotion;


    /* -------------------------------------------------------------------------- */
    /*                                  Functions                                 */
    /* -------------------------------------------------------------------------- */
    public double getRealPrice(){
        return this.getPrice() - ((this.getPrice() * this.getPromotion()) / 100);
    }
}
