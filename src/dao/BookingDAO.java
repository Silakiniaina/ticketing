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

    /* -------------------------------------------------------------------------- */
    /*                             Cancel a booking                               */
    /* -------------------------------------------------------------------------- */
    public void cancelBooking(Connection c, int bookingId) throws DaoException, SQLException {
        boolean isNewConnection = false;
        PreparedStatement prstmBooking = null;
        PreparedStatement prstmPassenger = null;
        String deletePassengerQuery = "DELETE FROM booking_passenger WHERE booking_id = ?";
        String deleteBookingQuery = "DELETE FROM booking WHERE id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);

            // Delete associated booking_passenger entries
            prstmPassenger = c.prepareStatement(deletePassengerQuery);
            prstmPassenger.setInt(1, bookingId);
            prstmPassenger.executeUpdate();

            // Delete the booking
            prstmBooking = c.prepareStatement(deleteBookingQuery);
            prstmBooking.setInt(1, bookingId);
            int affectedRow = prstmBooking.executeUpdate();
            if (affectedRow > 0) {
                c.commit();
            } else {
                c.rollback();
                throw new DaoException(deleteBookingQuery, "Booking not found");
            }
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(deleteBookingQuery, e.getMessage());
        } finally {
            Database.closeRessources(null, prstmPassenger, null, false);
            Database.closeRessources(null, prstmBooking, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*               Check if booking cancellation is on time                     */
    /* -------------------------------------------------------------------------- */
    public boolean isBookingCancellationOnTime(Connection c, int bookingId) throws DaoException, SQLException {
        try {
            // Retrieve booking, flight, and reservation settings
            Booking booking = getById(c, bookingId);
            if (booking == null || booking.getFlight() == null || booking.getFlight().getDepartureDatetime() == null) {
                return false;
            }
            ReservationSetting settings = reservationSettingDAO.getSetting(c);
            if (settings == null) {
                return false;
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime departureDateTime = booking.getFlight().getDepartureDatetime().toLocalDateTime();
            int hourLimitCanceling = settings.getHourLimitCanceling();

            if (departureDateTime.isBefore(currentDateTime) || 
                departureDateTime.minusHours(hourLimitCanceling).isBefore(currentDateTime) ||
                departureDateTime.minusHours(hourLimitCanceling).isEqual(currentDateTime)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new DaoException("Check booking cancellation on time", e.getMessage());
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get all bookings by user ID                         */
    /* -------------------------------------------------------------------------- */
    public List<Booking> getBookingByUserId(Connection c, int userId) throws DaoException, SQLException {
        List<Booking> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM booking WHERE user_id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, userId);
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
}