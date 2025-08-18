package model;

import lombok.Setter;

import java.sql.Date;

import lombok.Getter;

@Getter
@Setter
public class BookingPassenger {
    
    private int id; 
    private Booking booking; 
    private TypeSeat typeSeat; 
    private double price;
    private double promotion;
    private String passportFilePath;
    private int isPaid;


    /* -------------------------------------------------------------------------- */
    /*                                  Functions                                 */
    /* -------------------------------------------------------------------------- */
    public double getRealPrice(){
        return this.getPrice() - ((this.getPrice() * this.getPromotion()) / 100);
    }
}
