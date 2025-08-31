package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.FlightPromotion;
import util.Database;

public class FlightPromotionDAO {
    
    private TypeSeatDAO typeSeatDAO;
    private FlightDAO flightDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public FlightPromotionDAO(){
        this.typeSeatDAO = new TypeSeatDAO();
    }

    /* Constructor with FlightDAO for dependency injection */
    public FlightPromotionDAO(FlightDAO flightDAO, TypeSeatDAO typeSeatDAO) {
        this.flightDAO = flightDAO;
        this.typeSeatDAO = typeSeatDAO;
    }
    /* -------------------------------------------------------------------------- */
    /*                          Insert a flight promotion                         */
    /* -------------------------------------------------------------------------- */
    public FlightPromotion insert(Connection c, FlightPromotion f)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet generatedKeys = null;
        String query = "INSERT INTO flight_seat_promotion(type_seat_id, flight_id, seat_number, price, promotion_date) VALUES(?,?,?,?,?)";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setInt(1, f.getTypeSeat().getId());
            prstm.setInt(2, f.getFlight().getId());
            prstm.setInt(3, f.getSeatNumber());
            prstm.setDouble(4, f.getPrice());
            prstm.setDate(5, f.getPromotionDate());
            int affectedRow = prstm.executeUpdate();
            if (affectedRow > 0) {
                c.commit();
            }
            return f;
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally{
            Database.closeRessources(generatedKeys, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }


    /* -------------------------------------------------------------------------- */
    /*                      Get a flight promotion by flight                      */
    /* -------------------------------------------------------------------------- */
    public List<FlightPromotion> getByFlight(Connection c , int flightId) throws DaoException, SQLException{
        List<FlightPromotion> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM flight_seat_promotion WHERE flight_id = ? ORDER BY promotion_date ASC";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, flightId);
            rs = prstm.executeQuery();
            while(rs.next()){
                FlightPromotion t = new FlightPromotion();
                t.setFlight(flightDAO.getById(c, rs.getInt("flight_id")));
                t.setTypeSeat(typeSeatDAO.getById(c, rs.getInt("type_seat_id")));
                t.setSeatNumber(rs.getInt("seat_number"));
                t.setPrice(rs.getDouble("price"));
                t.setPromotionDate(rs.getDate("promotion_date"));
                result.add(t);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    public FlightPromotion getNextAfterDate(Connection c, int flightId, int typeSeatId, java.sql.Date date) throws DaoException, SQLException {
        FlightPromotion result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM flight_seat_promotion WHERE flight_id = ? AND type_seat_id = ? AND promotion_date > ? ORDER BY promotion_date ASC LIMIT 1";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, flightId);
            prstm.setInt(2, typeSeatId);
            prstm.setDate(3, date);
            rs = prstm.executeQuery();
            if (rs.next()) {
                result = new FlightPromotion();
                result.setFlight(flightDAO.getById(c, rs.getInt("flight_id")));
                result.setTypeSeat(typeSeatDAO.getById(c, rs.getInt("type_seat_id")));
                result.setSeatNumber(rs.getInt("seat_number"));
                result.setPrice(rs.getDouble("price"));
                result.setPromotionDate(rs.getDate("promotion_date"));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    public void updateSeatNumber(Connection c, FlightPromotion promotion) throws DaoException, SQLException {
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        String query = "UPDATE flight_seat_promotion SET seat_number = ? WHERE flight_id = ? AND type_seat_id = ? AND promotion_date = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setInt(1, promotion.getSeatNumber());
            prstm.setInt(2, promotion.getFlight().getId());
            prstm.setInt(3, promotion.getTypeSeat().getId());
            prstm.setDate(4, promotion.getPromotionDate());
            int affectedRow = prstm.executeUpdate();
            if (affectedRow > 0) {
                c.commit();
            }
        } catch (Exception e) {
            c.rollback();
            throw new DaoException(query, e.getMessage());
        } finally {
            Database.closeRessources(null, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}