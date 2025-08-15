package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FlightArg;
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
    /*                           Get a flight by its id                           */
    /* -------------------------------------------------------------------------- */
    public Flight getById(Connection c, int id)throws DaoException, SQLException{
        Flight result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM flight WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            if(rs.next()){
                result = new Flight();
                result.setId(rs.getInt("id"));
                result.setPlane(planeDAO.getById(c, rs.getInt("plane_id")));
                result.setDepartureCity(cityDAO.getById(c, rs.getInt("departure_city_id")));
                result.setArrivalCity(cityDAO.getById(c, rs.getInt("arrival_city_id")));
                result.setDepartureDatetime(rs.getTimestamp("departure_datetime"));
                result.setArrivalDatetime(rs.getTimestamp("arrival_datetime"));
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
    
    /* -------------------------------------------------------------------------- */
    /*                               Update a flight                              */
    /* -------------------------------------------------------------------------- */
    public void update(Connection c, Flight f)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "UPDATE flight SET departure_datetime = ?, arrival_datetime = ?, plane_id = ?, arrival_city_id = ?, departure_city_id = ? WHERE id = ?";
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
            prstm.setInt(6, f.getId());
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
    /*                               Delete a flight                              */
    /* -------------------------------------------------------------------------- */
    public void delete(Connection c, Flight f)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "DELETE FROM flight WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setInt(1, f.getId());
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
    /*                                Search flight                               */
    /* -------------------------------------------------------------------------- */
    public List<Flight> searchFlights(Connection c, FlightArg args) throws DaoException, SQLException {
        List<Flight> result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }

            StringBuilder query = new StringBuilder("SELECT * FROM flight WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (args.getPlaneId() != 0) {
                query.append(" AND plane_id = ?");
                params.add(args.getPlaneId());
            }
            if (args.getDepartureCityId() != 0) {
                query.append(" AND departure_city_id = ?");
                params.add(args.getDepartureCityId());
            }
            if (args.getArrivalCityId() != 0) {
                query.append(" AND arrival_city_id = ?");
                params.add(args.getArrivalCityId());
            }
            if (args.getDepartureDatetime() != null && !args.getDepartureDatetime().isEmpty()) {
                query.append(" AND DATE(departure_datetime) = ?");
                params.add(args.getDepartureDatetime());
            }
            if (args.getArrivalDatetime() != null && !args.getArrivalDatetime().isEmpty()) {
                query.append(" AND DATE(arrival_datetime) = ?");
                params.add(args.getArrivalDatetime());
            }

            prstm = c.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                prstm.setObject(i + 1, params.get(i));
            }

            rs = prstm.executeQuery();
            if(rs.wasNull()){
                return null;
            }else{
                result = new ArrayList<>();
                while (rs.next()) {
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
            }
        } catch (Exception e) {
            throw new DaoException("Search flights query", e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}