package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookingDAO;
import exception.DaoException;
import model.Booking;
import util.Database;

public class BookingService {

    private final BookingDAO bookingDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                               Get All bookings                             */
    /* -------------------------------------------------------------------------- */
    public List<Booking> getAll() throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingDAO.getAll(c);
    }

    /* -------------------------------------------------------------------------- */
    /*                           Get a booking by its id                          */
    /* -------------------------------------------------------------------------- */
    public Booking getById(int id) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingDAO.getById(c, id);
    }

    /* -------------------------------------------------------------------------- */
    /*                               Add a booking                                */
    /* -------------------------------------------------------------------------- */
    public Booking insert(Booking b) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingDAO.insert(c, b);
    }

    /* -------------------------------------------------------------------------- */
    /*                   Check if booking is on time                              */
    /* -------------------------------------------------------------------------- */
    public boolean isBookingOnTime(Booking b) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return bookingDAO.isBookingOnTime(c, b);
    }
}