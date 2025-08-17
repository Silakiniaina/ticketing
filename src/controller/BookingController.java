package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dto.BookingPassengerArg;
import dto.UpdateArg;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.controller.MySession;
import mg.dash.mvc.handler.views.ModelView;
import model.Booking;
import model.BookingPassenger;
import model.Flight;
import model.TypeSeat;
import model.User;
import service.BookingPassengerService;
import service.BookingService;
import service.FlightService;
import service.TypeSeatService;
import service.UserService;
import util.DateUtil;

@Controller
@Getter
@Setter
public class BookingController {

    MySession session;
    private final FlightService flightService;
    private final BookingService bookingService;
    private final UserService userService;
    private final TypeSeatService typeSeatService;
    private final BookingPassengerService bookingPassengerService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingController(){
        this.flightService = new FlightService();
        this.bookingService = new BookingService(); 
        this.userService = new UserService();
        this.typeSeatService = new TypeSeatService();
        this.bookingPassengerService = new BookingPassengerService();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                                Book a flight                               */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/booking")
    public ModelView bookFlight(@RequestParam("flight") UpdateArg arg){
        ModelView mv = new ModelView();
        if(arg != null){
            try {
                
                Flight f = flightService.getById(arg.getId());
                User u = (User)session.getUser();

                Booking b = new Booking();
                b.setUser(u);
                b.setFlight(f);
                b.setBookingDatetime(DateUtil.convertStringToTimestamp(LocalDateTime.now().toString()));
                b = bookingService.insert(b);

                String url = "booking/details?booking.id="+b.getId();
                mv.setUrl(url);
                mv.setRedirect(true);
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid flight ID format: " + nfe.getMessage());
                mv.setRedirect(false);
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO  " + daoEx.getMessage());
                mv.setRedirect(false);
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL  " + sqlEx.getMessage());
                mv.setRedirect(false);
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
                mv.setRedirect(false);
            }
        }
        return mv;
    } 

    /* -------------------------------------------------------------------------- */
    /*                               Booking details                              */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/booking/details")
    public ModelView showBookingDetailsForm(@RequestParam("booking") UpdateArg arg){
        ModelView mv = new ModelView("/WEB-INF/views/layout/client-layout.jsp");
        mv.addObject("contentPage", "pages/client/booking/booking-details-form.jsp");
        if(arg != null){
            try {
                Booking b = bookingService.getById(arg.getId());
                List<TypeSeat> typeSeats = typeSeatService.getAll();
                List<BookingPassenger> bookingPassengers = bookingPassengerService.getAllByBookingId(arg.getId());
                
                String pageTitle  = "Booking number : "+b.getId();
                mv.addObject("pageTitle", pageTitle);
                mv.addObject("typeSeats", typeSeats);
                mv.addObject("booking", b);
                mv.addObject("bookingPassengers", bookingPassengers);
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid booking ID format: " + nfe.getMessage());
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO  " + daoEx.getMessage());
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL  " + sqlEx.getMessage());
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
            }
        }else{
            mv.addObject("error", "No booking id specified");
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                           Manage booking details                           */
    /* -------------------------------------------------------------------------- */
    @Post
    @Url("/flights/booking/details")
    public ModelView manageBookingDetails(@RequestParam("booking") BookingPassengerArg arg){
        ModelView mv = new ModelView(); 
        if(arg != null){
            try {
                for(int i=0; i<arg.getSeatNumber(); i++){
                    BookingPassenger bp = bookingPassengerService.injectValues(new BookingPassenger(), arg);
                    bp.setPrice(flightService.getSeatPriceByFlightAndTypeSeat(bp.getBooking().getFlight().getId(), arg.getTypeSeatId()));
                    bp.setPromotion(flightService.getPromotion(bp.getBooking().getFlight().getId(), arg.getTypeSeatId()));
                    bookingPassengerService.addBookingPassenger(bp);
                }
                String url = "details?booking.id="+arg.getBookingId();
                mv.setUrl(url);
                mv.setRedirect(true);
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO  " + daoEx.getMessage());
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL  " + sqlEx.getMessage());
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
            }
        }
        return mv;
    }
}
