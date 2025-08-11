package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.DaoException;
import model.TypeSeat;
import util.Database;

public class TypeSeatDAO {
    
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
