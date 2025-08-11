package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import model.Plane;
import util.Database;

public class PlaneDAO {

    private PlaneSeatDAO planeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public PlaneDAO(){
        this.planeSeatDAO = new PlaneSeatDAO();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                               Get all planes                               */
    /* -------------------------------------------------------------------------- */
    public List<Plane> getAll(Connection c)throws DaoException, SQLException{
        List<Plane> result = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM plane";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            while(rs.next()){
                Plane p = new Plane();
                p.setId(rs.getInt("id"));
                p.setFabricationDate(rs.getDate("fabrication_date"));
                p.setModel(rs.getString("model"));
                p.setSeats(planeSeatDAO.getByPlaneId(c, p));
                result.add(p);
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                            Get a plane by its id                           */
    /* -------------------------------------------------------------------------- */
    public Plane getById(Connection c, int id)throws DaoException, SQLException{
        Plane result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM plane WHERE id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            while(rs.next()){
                result = new Plane();
                result.setId(rs.getInt("id"));
                result.setFabricationDate(rs.getDate("fabrication_date"));
                result.setModel(rs.getString("model"));
                result.setSeats(planeSeatDAO.getByPlaneId(c, result));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}
