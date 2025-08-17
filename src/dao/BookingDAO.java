package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.Booking;
import model.ReservationSetting;
import util.Database;

public class BookingDAO {

    private UserDAO userDAO;
    private FlightDAO flightDAO;
    private ReservationSettingDAO reservationSettingDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingDAO() {
        this.userDAO = new UserDAO();
        this.flightDAO = new FlightDAO();
        this.reservationSettingDAO = new ReservationSettingDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                               Get All bookings                             */
    /* -------------------------------------------------------------------------- */
    public List<Booking> getAll(Connection c) throws DaoException, SQLException {
        List<Booking> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM booking";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setBookingDatetime(rs.getTimestamp("booking_datetime"));
                b.setUser(userDAO.getById(c, rs.getInt("user_id")));
                b.setFlight(flightDAO.getById(c, rs.getInt("flight_id")));
                result.add(b);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                           Get a booking by its id                          */
    /* -------------------------------------------------------------------------- */
    public Booking getById(Connection c, int id) throws DaoException, SQLException {
        Booking result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM booking WHERE id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            if (rs.next()) {
                result = new Booking();
                result.setId(rs.getInt("id"));
                result.setBookingDatetime(rs.getTimestamp("booking_datetime"));
                result.setUser(userDAO.getById(c, rs.getInt("user_id")));
                result.setFlight(flightDAO.getById(c, rs.getInt("flight_id")));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                               Insert a booking                             */
    /* -------------------------------------------------------------------------- */
    public Booking insert(Connection c, Booking b) throws DaoException, SQLException {
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet generatedKeys = null;
        String query = "INSERT INTO booking (booking_datetime, user_id, flight_id) VALUES (?, ?, ?)";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            prstm.setTimestamp(1, b.getBookingDatetime());
            prstm.setInt(2, b.getUser().getId());
            prstm.setInt(3, b.getFlight().getId());
            int affectedRow = prstm.executeUpdate();
            if (affectedRow > 0) {
                generatedKeys = prstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    b.setId(generatedKeys.getInt(1));
                }
                c.commit();
            }
            return b;
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(generatedKeys, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                   Check if booking is on time                              */
    /* -------------------------------------------------------------------------- */
    public boolean isBookingOnTime(Connection c, Booking b) throws DaoException, SQLException {
        try {
            ReservationSetting settings = reservationSettingDAO.getSetting(c);
            if (b.getFlight() == null || settings == null || b.getBookingDatetime() == null) {
                return false;
            }

            LocalDateTime bookingDateTime = b.getBookingDatetime().toLocalDateTime();
            LocalDateTime departureDateTime = b.getFlight().getDepartureDatetime().toLocalDateTime();
            int hourLimitReserving = settings.getHourLimitReserving();

            if (departureDateTime.isBefore(bookingDateTime) || 
                departureDateTime.minusHours(hourLimitReserving).isBefore(bookingDateTime) ||
                departureDateTime.minusHours(hourLimitReserving).isEqual(bookingDateTime)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new DaoException("Check booking on time", e.getMessage());
        }
    }
}