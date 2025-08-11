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
                f.setPlane(planeDAO.getById(c, f.getId()));
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

}