package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.FlightDAO;
import dao.FlightPromotionDAO;
import dao.TypeSeatDAO;
import dto.FlightPromotionArg;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import model.FlightPromotion;
import util.Database;

@Getter
@Setter
public class FlightPromotionService {

    private final FlightPromotionDAO flightPromotionDAO;
    private final FlightDAO flightDAO;
    private final TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public FlightPromotionService(){
        this.flightPromotionDAO = new FlightPromotionDAO();
        this.flightDAO = new FlightDAO();
        this.typeSeatDAO = new TypeSeatDAO();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                             Insert a promotion                             */
    /* -------------------------------------------------------------------------- */
    public void insert(FlightPromotion f)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        flightPromotionDAO.insert(c, f);
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get a promotions of a flight                        */
    /* -------------------------------------------------------------------------- */
    public List<FlightPromotion> getByFlight(int flightId) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return flightPromotionDAO.getByFlight(c, flightId);
    }

    /* -------------------------------------------------------------------------- */
    /*                                Inject values                               */
    /* -------------------------------------------------------------------------- */
    public FlightPromotion injectValues(FlightPromotion f, FlightPromotionArg args)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        if(f == null){
            throw new Exception("Cannot inject values on a flight that is null");
        }
        f.setFlight(flightDAO.getById(c, args.getFlightId()));
        f.setTypeSeat(typeSeatDAO.getById(c, args.getTypeSeatId()));
        f.setSeatNumber(args.getSeatNumber());
        f.setPercentage(args.getPercentage());
        return f;
    }
}
