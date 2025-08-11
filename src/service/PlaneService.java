package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.PlaneDAO;
import exception.DaoException;
import model.Plane;
import util.Database;

public class PlaneService {
    
    private final PlaneDAO planeDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public PlaneService(){
        this.planeDAO = new PlaneDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                                Get all plane                               */
    /* -------------------------------------------------------------------------- */
    public List<Plane> getAll() throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return planeDAO.getAll(c);
    }
}

