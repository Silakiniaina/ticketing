package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.DaoException;
import model.ReservationSetting;
import util.Database;

public class ReservationSettingDAO {
    
    /* -------------------------------------------------------------------------- */
    /*                             Update the settings                            */
    /* -------------------------------------------------------------------------- */
    public void update(Connection c, ReservationSetting r)throws DaoException, SQLException{
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        String query = "UPDATE reservation_setting SET hour_limit_reserving = ?, hour_limit_canceling = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            c.setAutoCommit(false);
            prstm = c.prepareStatement(query);
            prstm.setInt(1, r.getHourLimitReserving());
            prstm.setInt(2, r.getHourLimitCanceling());
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
    /*                                 Get setting                                */
    /* -------------------------------------------------------------------------- */
    public ReservationSetting getSetting(Connection c) throws SQLException, DaoException{
        ReservationSetting result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM reservation_setting";
        try {
            if(c == null){
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            rs = prstm.executeQuery();
            if(rs.next()){
                result = new ReservationSetting();
                result.setHourLimitReserving(rs.getInt("hour_limit_reserving"));
                result.setHourLimitCanceling(rs.getInt("hour_limit_canceling"));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}
