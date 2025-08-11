package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CityDAO;
import exception.DaoException;
import model.City;
import util.Database;

public class CityService {
    
    private CityDAO cityDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public CityService(){
        this.cityDAO = new CityDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                               Get all cities                               */
    /* -------------------------------------------------------------------------- */
    public List<City> getAll()throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return cityDAO.getAll(c);
    }
}
