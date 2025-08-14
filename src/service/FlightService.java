package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CityDAO;
import dao.FlightDAO;
import dao.PlaneDAO;
import dto.FlightArg;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import model.Flight;
import util.Database;
import util.DateUtil;

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

    /* -------------------------------------------------------------------------- */
    /*                           Get a flight by its id                           */
    /* -------------------------------------------------------------------------- */
    public Flight getById(int id) throws DaoException,SQLException,Exception{
        Connection c = Database.getActiveConnection();
        return flightDAO.getById(c, id);
    }

    /* -------------------------------------------------------------------------- */
    /*                                Add a flight                                */
    /* -------------------------------------------------------------------------- */
    public void addFlight(Flight f) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        flightDAO.insert(c, f);
    }

    /* -------------------------------------------------------------------------- */
    /*                                Update flight                               */
    /* -------------------------------------------------------------------------- */
    public void updateFlight(Flight f) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        flightDAO.update(c, f);
    }

    /* -------------------------------------------------------------------------- */
    /*                  Inject flightArg value on Flight instance                 */
    /* -------------------------------------------------------------------------- */
    public Flight injectValues(Flight f, FlightArg args)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        if(f == null){
            throw new Exception("Cannot inject values on a flight that is null");
        }
        f.setId(args.getId());
        f.setPlane(planeDAO.getById(c, args.getPlaneId()));
        f.setArrivalCity(cityDAO.getById(c, args.getArrivalCityId()));
        f.setDepartureCity(cityDAO.getById(c, args.getDepartureCityId()));
        f.setDepartureDatetime(DateUtil.convertStringToTimestamp(args.getDepartureDatetime()));
        f.setArrivalDatetime(DateUtil.convertStringToTimestamp(args.getDepartureDatetime()));
        return f;
    }
}
