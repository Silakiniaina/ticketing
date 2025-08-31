package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.CleanupArg;
import dto.BookingDateArg;
import dto.BookingPassengerArg;
import dto.BookingPassengerPaymentArg;
import dto.UpdateArg;
import exception.DaoException;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.controller.MySession;
import mg.dash.mvc.handler.views.ModelView;
import mg.dash.mvc.util.FileUtils;
import model.Booking;
import model.BookingPassenger;
import model.Flight;
import model.FlightPromotion;
import model.TypeSeat;
import model.User;
import service.BookingPassengerService;
import service.BookingService;
import service.FlightPromotionService;
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
    private final FlightPromotionService flightPromotionService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingController(){
        this.flightService = new FlightService();
        this.bookingService = new BookingService(); 
        this.userService = new UserService();
        this.typeSeatService = new TypeSeatService();
        this.bookingPassengerService = new BookingPassengerService();
        this.flightPromotionService = new FlightPromotionService();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                                Book a flight                               */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/booking-date")
    public ModelView dateBooking(@RequestParam("flight") UpdateArg arg){
        ModelView mv = new ModelView("/WEB-INF/views/layout/client-layout.jsp");
        mv.addObject("contentPage", "pages/client/booking/booking-date-form.jsp");
        if(arg != null){
            try {
                
                Flight f = flightService.getById(arg.getId());
                mv.addObject("flight", f);
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

    @Get
    @Url("/flights/booking")
    public ModelView bookFlight(@RequestParam("booking") BookingDateArg arg){
        ModelView mv = new ModelView("/user/flights");
        if(arg != null){
            try {
                Flight f = flightService.getById(arg.getFlightId());
                User u = (User)session.getUser();

                Booking b = new Booking();
                b.setUser(u);
                b.setFlight(f);
                b.setBookingDatetime(DateUtil.convertStringToTimestamp(arg.getBookingDate()+"T00:00:00"));
                if(bookingService.isBookingOnTime(b)){
                    b = bookingService.insert(b);
                    String url = "booking/details?booking.id="+b.getId();
                    mv.setUrl(url);
                    mv.setRedirect(true);
                }else{
                    mv.setUrl("/user/flights");
                    mv.addObject("error", "The booking for this flight is closed");
                }

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
        }else{
            mv.addObject("error", "Booking Date Arg is null");
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
                    bp.setPrice(flightService.getPromotion(bp.getBooking().getFlight().getId(), arg.getTypeSeatId(), bp.getBooking().getBookingDatetime()));
                    bp.setPromotion(0);
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

    /* -------------------------------------------------------------------------- */
    /*                             Cancel a booking                               */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/booking/cancel")
    public ModelView cancelBooking(@RequestParam("booking") UpdateArg arg) {
        ModelView mv = new ModelView();
        if (arg != null) {
            try {
                int bookingId = arg.getId();
                if (bookingService.isBookingCancellationOnTime(bookingId)) {
                    bookingService.cancelBooking(bookingId);
                    mv.setUrl("/ticketing/user/bookings");
                    mv.addObject("success", "Booking successfully canceled");
                    mv.setRedirect(true);
                } else {
                    mv.setUrl("/user/bookings");
                    mv.addObject("error", "Cancellation period for this booking has expired");
                }
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid booking ID format: " + nfe.getMessage());
                mv.setRedirect(false);
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO: " + daoEx.getMessage());
                mv.setRedirect(false);
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL: " + sqlEx.getMessage());
                mv.setRedirect(false);
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
                mv.setRedirect(false);
            }
        } else {
            mv.addObject("error", "No booking ID specified");
            mv.setRedirect(false);
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get all bookings for user                           */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/user/bookings")
    public ModelView getUserBookings() {
        ModelView mv = new ModelView("/WEB-INF/views/layout/client-layout.jsp");
        mv.addObject("contentPage", "pages/client/booking/user-bookings.jsp");
        try {
            User u = (User) session.getUser();
            if (u == null) {
                mv.addObject("error", "User not logged in");
                mv.setUrl("/login");
                mv.setRedirect(true);
                return mv;
            }
            List<Booking> bookings = bookingService.getBookingByUserId(u.getId());
            mv.addObject("pageTitle", "My Bookings");
            mv.addObject("bookings", bookings);
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO: " + daoEx.getMessage());
        } catch (SQLException sqlEx) {
            mv.addObject("error", "Error on SQL: " + sqlEx.getMessage());
        } catch (Exception ex) {
            mv.addObject("error", "Unexpected error: " + ex.getMessage());
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                        Show upload form for passport                       */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/booking/passport-upload")
    public ModelView showUploadForm(@RequestParam("booking") UpdateArg arg){
        ModelView mv = new ModelView("/WEB-INF/views/layout/client-layout.jsp");
        mv.addObject("contentPage", "pages/client/booking/passport-upload.jsp");
        if(arg != null){
            try {
                mv.addObject("bp", arg.getId());
            } catch (Exception e) {
                mv.addObject("error", "Unexpected error: " + e.getMessage());
            }
        }
        return mv;
    }

    @Post
    @Url("/booking/passport-upload")
    public ModelView uploadPassport(@RequestParam("file") Part file, @RequestParam("id") String id){
        ModelView mv = new ModelView();
        try {
            if (!(file instanceof Part)) {
                throw new IllegalArgumentException("Invalid file upload type: " + file.getClass().getName());
            }
            String uploadDir = "passport";
            String filePath = FileUtils.uploadFile(file, uploadDir,true);
            BookingPassenger bp = bookingPassengerService.getById(Integer.valueOf(id));
            bp.setPassportFilePath(filePath);
            mv.setUrl("/ticketing/user/bookings");
            mv.addObject("success", "File uploaded successfully");
            mv.setRedirect(true);
        } catch (IllegalArgumentException nfe) {
            mv.addObject("error", nfe.getMessage());
            mv.setRedirect(false);
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO: " + daoEx.getMessage());
            mv.setRedirect(false);
        } catch (SQLException sqlEx) {
            mv.addObject("error", "Error on SQL: " + sqlEx.getMessage());
            mv.setRedirect(false);
        } catch (Exception ex) {
            mv.addObject("error", "Unexpected error: " + ex.getMessage());
            mv.setRedirect(false);
        }
        return mv;
    }

    @Get
    @Url("/booking/payment")
    public ModelView showPaymentForm(@RequestParam("booking") UpdateArg arg){
        ModelView mv = new ModelView();
        if(arg != null){
            try {
                BookingPassenger bookingPassenger = bookingPassengerService.getById(arg.getId());
                bookingPassengerService.pay(arg.getId());
                String url = "/ticketing/flights/booking/details?booking.id="+bookingPassenger.getBooking().getId();
                mv.setUrl(url);
                mv.addObject("pageTitle", "Booking");
                mv.setRedirect(true);
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid booking ID format: " + nfe.getMessage());
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

    @Post
    @Url("/booking/payment")
    public ModelView uploadPassport(@RequestParam("booking") BookingPassengerPaymentArg arg){
        ModelView mv = new ModelView();
        if(arg != null){
            try {

                BookingPassenger bp = bookingPassengerService.getById(arg.getBookingId());
                mv.setUrl("/ticketing/user/bookings");
                mv.addObject("success", "File uploaded successfully");
                mv.setRedirect(true);
            } catch (IllegalArgumentException nfe) {
                mv.addObject("error", nfe.getMessage());
                mv.setRedirect(false);
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO: " + daoEx.getMessage());
                mv.setRedirect(false);
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL: " + sqlEx.getMessage());
                mv.setRedirect(false);
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
                mv.setRedirect(false);
            }
        }
        return mv;
    }

    @Get
    @Url("/bookings/cleanup")
    public ModelView cleanUp(@RequestParam("cleanup") CleanupArg arg) {
        ModelView mv = new ModelView("/ticketing/user/bookings");
        mv.setRedirect(true);
        try {
            Date sqlDate = Date.valueOf(LocalDate.parse(arg.getDate()));
            List<BookingPassenger> unpaidPassengers = bookingPassengerService.getAllUnpaid();
            for (BookingPassenger bp : unpaidPassengers) {
                int flightId = bp.getBooking().getFlight().getId();
                int typeSeatId = bp.getTypeSeat().getId();
                int bookingId = bp.getBooking().getId();
                FlightPromotion nextPromotion = flightPromotionService.getNextAfterDate(flightId, typeSeatId, sqlDate);
                if (nextPromotion != null) {
                    nextPromotion.setSeatNumber(nextPromotion.getSeatNumber() + 1);
                    flightPromotionService.updateSeatNumber(nextPromotion);
                }
                bookingService.cancelBooking(bookingId);
            }
            mv.addObject("success", "Cleanup completed successfully");
        } catch (NumberFormatException nfe) {
            mv.addObject("error", "Invalid date format: " + nfe.getMessage());
            mv.setRedirect(false);
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO: " + daoEx.getMessage());
            mv.setRedirect(false);
        } catch (SQLException sqlEx) {
            mv.addObject("error", "Error on SQL: " + sqlEx.getMessage());
            mv.setRedirect(false);
        } catch (Exception ex) {
            mv.addObject("error", "Unexpected error: " + ex.getMessage());
            mv.setRedirect(false);
        }
        return mv;
    }
}