package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.TypeSeat;
import util.Database;

public class TypeSeatDAO {

    /* -------------------------------------------------------------------------- */
    /*                              Get all type seat                             */
    /* -------------------------------------------------------------------------- */
    public List<TypeSeat> getAll(Connection c) throws DaoException,SQLException{
        List<TypeSeat> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM type_seat";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            while(rs.next()){
                TypeSeat t = new TypeSeat();
                t.setId(rs.getInt("id"));
                t.setLabel(rs.getString("label"));
                result.add(t);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
    
    /* -------------------------------------------------------------------------- */
    /*                       Function to get type seat by its id                  */
    /* -------------------------------------------------------------------------- */
    public TypeSeat getById(Connection c, int id) throws DaoException,SQLException{
        TypeSeat result = null; 
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM type_seat WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            if(rs.next()){
                result = new TypeSeat();
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
