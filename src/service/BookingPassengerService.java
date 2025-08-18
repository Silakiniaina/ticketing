package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookingDAO;
import dao.BookingPassengerDAO;
import dao.TypeSeatDAO;
import dto.BookingPassengerArg;
import exception.DaoException;
import model.BookingPassenger;
import util.Database;

public class BookingPassengerService {

    private final BookingPassengerDAO bookingPassengerDAO;
    private final BookingDAO bookingDAO;
    private final TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingPassengerService() {
        this.bookingPassengerDAO = new BookingPassengerDAO();
        this.bookingDAO = new BookingDAO();
        this.typeSeatDAO = new TypeSeatDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get all booking passengers by booking ID            */
    /* -------------------------------------------------------------------------- */
    public List<BookingPassenger> getAllByBookingId(int bookingId) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingPassengerDAO.getAllByBookingId(c, bookingId);
    }

    /* -------------------------------------------------------------------------- */
    /*                                  Get by id                                 */
    /* -------------------------------------------------------------------------- */
    public BookingPassenger getById(int bookingId) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingPassengerDAO.getById(c, bookingId);
    }

    /* -------------------------------------------------------------------------- */
    /*                          Add a booking passenger                           */
    /* -------------------------------------------------------------------------- */
    public BookingPassenger addBookingPassenger(BookingPassenger bp) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingPassengerDAO.insert(c, bp);
    }

    /* -------------------------------------------------------------------------- */
    /*                           Add passport file path                           */
    /* -------------------------------------------------------------------------- */
    public void addPassportFilePath(BookingPassenger bp) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        bookingPassengerDAO.addPassportFilePath(c, bp);
    }

    /* -------------------------------------------------------------------------- */
    /*                               Injects values                               */
    /* -------------------------------------------------------------------------- */
    public BookingPassenger injectValues(BookingPassenger bp,BookingPassengerArg bpa)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        if(bp == null){
            throw new Exception("Cannot inject values on a booking passenger that is null");
        }
        bp.setBooking(bookingDAO.getById(c, bpa.getBookingId()));
        bp.setTypeSeat(typeSeatDAO.getById(c, bpa.getTypeSeatId()));
        return bp;
    }
}