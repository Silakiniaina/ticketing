package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookingDAO;
import dao.FlightDAO;
import dao.UserDAO;
import exception.DaoException;
import model.Booking;
import util.Database;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final UserDAO userDAO;
    private final FlightDAO flightDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
        this.flightDAO = new FlightDAO();
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
}