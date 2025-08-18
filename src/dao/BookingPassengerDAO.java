package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.BookingPassenger;
import model.ReservationSetting;
import util.Database;

public class BookingPassengerDAO {

    private BookingDAO bookingDAO;
    private TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public BookingPassengerDAO() {
        this.bookingDAO = new BookingDAO();
        this.typeSeatDAO = new TypeSeatDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get all booking passengers by booking ID            */
    /* -------------------------------------------------------------------------- */
    public List<BookingPassenger> getAllByBookingId(Connection c, int bookingId) throws DaoException, SQLException {
        List<BookingPassenger> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM booking_passenger WHERE booking_id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, bookingId);
            rs = prstm.executeQuery();
            while (rs.next()) {
                BookingPassenger bp = new BookingPassenger();
                bp.setId(rs.getInt("id"));
                bp.setBooking(bookingDAO.getById(c, rs.getInt("booking_id")));
                bp.setTypeSeat(typeSeatDAO.getById(c, rs.getInt("type_seat_id")));
                bp.setPrice(rs.getDouble("price"));
                bp.setPromotion(rs.getDouble("promotion"));
                bp.setIsPaid(rs.getInt("ispaid"));
                result.add(bp);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                         Get booking passenger by id                        */
    /* -------------------------------------------------------------------------- */
    public BookingPassenger getById(Connection c, int bpId) throws DaoException, SQLException {
        BookingPassenger result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM booking_passenger WHERE id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, bpId);
            rs = prstm.executeQuery();
            if (rs.next()) {
                result = new BookingPassenger();
                result.setId(rs.getInt("id"));
                result.setBooking(bookingDAO.getById(c, rs.getInt("booking_id")));
                result.setTypeSeat(typeSeatDAO.getById(c, rs.getInt("type_seat_id")));
                result.setPrice(rs.getDouble("price"));
                result.setPromotion(rs.getDouble("promotion"));
                result.setIsPaid(rs.getInt("ispaid"));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                          Insert a booking passenger                        */
    /* -------------------------------------------------------------------------- */
    public BookingPassenger insert(Connection c, BookingPassenger bp) throws DaoException, SQLException {
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet generatedKeys = null;
        String query = "INSERT INTO booking_passenger (booking_id, type_seat_id, price, promotion) VALUES (?, ?, ?, ?)";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            prstm.setInt(1, bp.getBooking().getId());
            prstm.setInt(2, bp.getTypeSeat().getId());
            prstm.setDouble(3, bp.getPrice());
            prstm.setDouble(4, bp.getPromotion());
            int affectedRow = prstm.executeUpdate();
            if (affectedRow > 0) {
                generatedKeys = prstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bp.setId(generatedKeys.getInt(1));
                }
                c.commit();
            }
            return bp;
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(generatedKeys, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                           Add passport file path                           */
    /* -------------------------------------------------------------------------- */
    public void addPassportFilePath(Connection c, BookingPassenger bp) throws DaoException, SQLException {
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet generatedKeys = null;
        String query = "UPDATE booking_passenger SET passport_file_path = ? WHERE booking_passenger_id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setString(1, bp.getPassportFilePath());
            prstm.setInt(2, bp.getId());
            int affectedRow = prstm.executeUpdate();
            if (affectedRow > 0) {
                c.commit();
            }
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(generatedKeys, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    public void pay(Connection c, int r)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "UPDATE booking_passenger SET ispaid = ? WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setInt(1, 1);
            prstm.setInt(2, r);
            int affectedRow = prstm.executeUpdate();
            if(affectedRow > 0){
                c.commit();
            }
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally{
            Database.closeRessources(null, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}