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
    
    private final FlightDAO flightDAO;
    private final TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public FlightPromotionDAO(){
        this.flightDAO = new FlightDAO();
        this.typeSeatDAO = new TypeSeatDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                          Insert a flight promotion                         */
    /* -------------------------------------------------------------------------- */
    public void insert(Connection c, FlightPromotion f)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "INSERT INTO flight_seat_promotion(type_seat_id, flight_id, seat_number, percentage) VALUES(?,?,?,?)";
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
            prstm.setDouble(4, f.getPercentage());
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


    /* -------------------------------------------------------------------------- */
    /*                      Get a flight promotion by flight                      */
    /* -------------------------------------------------------------------------- */
    public List<FlightPromotion> getByFlight(Connection c , int flightId) throws DaoException, SQLException{
        List<FlightPromotion> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM flight_seat_promotion WHERE flight_id = ?";
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
                t.setPercentage(rs.getDouble("percentage"));
                result.add(t);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}
