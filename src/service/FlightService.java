package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.CityDAO;
import dao.FlightDAO;
import dao.FlightPromotionDAO;
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
        this.planeDAO = new PlaneDAO();
        this.cityDAO = new CityDAO();
        this.flightDAO = new FlightDAO(this.planeDAO, this.cityDAO, new FlightPromotionDAO());
    }

    /* -------------------------------------------------------------------------- */
    /*                               Get All flight                               */
    /* -------------------------------------------------------------------------- */
    public List<Flight> getAll() throws DaoException,SQLException,Exception{
        Connection c = Database.getActiveConnection();
        return flightDAO.getAll(c);
    }

    /* -------------------------------------------------------------------------- */
    /*                                Search flight                               */
    /* -------------------------------------------------------------------------- */
    public List<Flight> search(FlightArg arg) throws DaoException,SQLException,Exception{
        Connection c = Database.getActiveConnection();
        return flightDAO.searchFlights(c,arg);
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
    /*                               Delete a flight                              */
    /* -------------------------------------------------------------------------- */
    public void deleteFlight(Flight f) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        flightDAO.delete(c, f);
    }
    
    /* -------------------------------------------------------------------------- */
    /*                 Get seat price by flight ID and type seat ID               */
    /* -------------------------------------------------------------------------- */
    public double getSeatPriceByFlightAndTypeSeat(int flightId, int typeSeatId) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return flightDAO.getSeatPriceByFlightAndTypeSeat(c, flightId, typeSeatId);
    }

    /* -------------------------------------------------------------------------- */
    /*             Get promotion by flight ID, type seat ID and date              */
    /* -------------------------------------------------------------------------- */
    public double getPromotion(int flightId, int typeSeatId, Timestamp bookingDateTime) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return flightDAO.getPromotion(c, flightId, typeSeatId, bookingDateTime.toLocalDateTime().toLocalDate());
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