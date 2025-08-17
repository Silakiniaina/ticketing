package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.ReservationSettingDAO;
import exception.DaoException;
import model.ReservationSetting;
import util.Database;

public class ReservationSettingService {

    private final ReservationSettingDAO reservationSettingDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public ReservationSettingService(){
        this.reservationSettingDAO = new ReservationSettingDAO();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                               Update setting                               */
    /* -------------------------------------------------------------------------- */
    public void update(ReservationSetting r)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        reservationSettingDAO.update(c, r);
    }

    /* -------------------------------------------------------------------------- */
    /*                                 Get setting                                */
    /* -------------------------------------------------------------------------- */
    public ReservationSetting getSetting()throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return reservationSettingDAO.getSetting(c);
    }
}
