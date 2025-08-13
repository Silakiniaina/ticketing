package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.Flight;
import util.Database;

public class FlightDAO {

    private PlaneDAO planeDAO;
    private CityDAO cityDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public FlightDAO(){
        this.planeDAO = new PlaneDAO();
        this.cityDAO = new CityDAO();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                               Get All flights                              */
    /* -------------------------------------------------------------------------- */
    public List<Flight> getAll(Connection c)throws DaoException, SQLException{
        List<Flight> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM flight";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            while(rs.next()){
                Flight f = new Flight();
                f.setId(rs.getInt("id"));
                f.setPlane(planeDAO.getById(c, rs.getInt("plane_id")));
                f.setDepartureCity(cityDAO.getById(c, rs.getInt("departure_city_id")));
                f.setArrivalCity(cityDAO.getById(c, rs.getInt("arrival_city_id")));
                f.setDepartureDatetime(rs.getTimestamp("departure_datetime"));
                f.setArrivalDatetime(rs.getTimestamp("arrival_datetime"));
                result.add(f);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }


    /* -------------------------------------------------------------------------- */
    /*                               Insert a flight                              */
    /* -------------------------------------------------------------------------- */
    public void insert(Connection c, Flight f)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "INSERT INTO flight(departure_datetime, arrival_datetime, plane_id, arrival_city_id, departure_city_id) VALUES(?,?,?,?,?)";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setTimestamp(1, f.getDepartureDatetime());
            prstm.setTimestamp(2, f.getArrivalDatetime());
            prstm.setInt(3, f.getPlane().getId());
            prstm.setInt(4, f.getArrivalCity().getId());
            prstm.setInt(5, f.getDepartureCity().getId());
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