package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
        this.flightDAO = new FlightDAO();
        this.typeSeatDAO = new TypeSeatDAO();
        this.flightPromotionDAO = new FlightPromotionDAO(this.flightDAO, this.typeSeatDAO);
    }
    
    /* -------------------------------------------------------------------------- */
    /*                             Insert a promotion                             */
    /* -------------------------------------------------------------------------- */
    public FlightPromotion insert(FlightPromotion f)throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return flightPromotionDAO.insert(c, f);
    }

    /* -------------------------------------------------------------------------- */
    /*                        Get a promotions of a flight                        */
    /* -------------------------------------------------------------------------- */
    public List<FlightPromotion> getByFlight(int flightId) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return flightPromotionDAO.getByFlight(c, flightId);
    }

    public FlightPromotion getNextAfterDate(int flightId, int typeSeatId, Date date) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        return flightPromotionDAO.getNextAfterDate(c, flightId, typeSeatId, date);
    }

    public void updateSeatNumber(FlightPromotion promotion) throws DaoException, SQLException, Exception {
        Connection c = Database.getActiveConnection();
        flightPromotionDAO.updateSeatNumber(c, promotion);
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
        f.setPrice(args.getPrice());
        f.setPromotionDate(Date.valueOf(LocalDate.parse(args.getPromotionDate())));
        return f;
    }
}