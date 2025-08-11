package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CityDAO;
import dao.FlightDAO;
import dao.PlaneDAO;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import model.Flight;
import util.Database;

@Getter
@Setter
public class FlightService {
    
    private final FlightDAO flightDAO;
    private final CityDAO cityDAO; 
    private final PlaneDAO planeDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public FlightService(){
        this.flightDAO = new FlightDAO();
        this.cityDAO = new CityDAO();
        this.planeDAO = new PlaneDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                               Get All flight                               */
    /* -------------------------------------------------------------------------- */
    public List<Flight> getAll() throws DaoException,SQLException,Exception{
        Connection c = Database.getActiveConnection();
        return flightDAO.getAll(c);
    }
}
