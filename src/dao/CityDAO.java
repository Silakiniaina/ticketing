package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.City;
import util.Database;

public class CityDAO {
    
    /* -------------------------------------------------------------------------- */
    /*                       Function to get get all cities                       */
    /* -------------------------------------------------------------------------- */
    public List<City> getAll(Connection c)throws DaoException, SQLException{
        List<City> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM city";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            while(rs.next()){
                City r = new City();
                r.setId(rs.getInt("id"));
                r.setLabel(rs.getString("label"));
                result.add(r);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
    
    /* -------------------------------------------------------------------------- */
    /*                       Function to get city by its id                       */
    /* -------------------------------------------------------------------------- */
    public City getById(Connection c, int id) throws DaoException,SQLException{
        City result = null; 
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM city WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            if(rs.next()){
                result = new City();
                result.setId(rs.getInt("id"));
                result.setLabel(rs.getString("label"));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}
