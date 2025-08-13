package controller;

import java.sql.SQLException;
import java.util.List;

import dto.FlightArg;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import mg.dash.mvc.annotation.Auth;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.handler.views.ModelView;
import model.City;
import model.Flight;
import model.Plane;
import service.CityService;
import service.FlightService;
import service.PlaneService;

@Controller
@Auth
@Getter
@Setter
public class FlightController{
    
    private final FlightService flightService;
    private final CityService cityService;
    private final PlaneService planeService;

    /* -------------------------------------------------------------------------- */
    /*                                  Construct                                 */
    /* -------------------------------------------------------------------------- */
    public FlightController(){
        this.flightService = new FlightService();
        this.cityService = new CityService();
        this.planeService = new PlaneService();
    }

    /* -------------------------------------------------------------------------- */
    /*                            Show fligth list page                           */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights")
    public ModelView showFlightList(){
        ModelView mv = new ModelView("/WEB-INF/views/layout/admin-layout.jsp");
        try {
            List<Flight> flights = flightService.getAll();
            if(flights == null){
                throw new Exception("Flights is null");
            }
            String pageTitle  = "List of "+flights.size()+ " flights";
            mv.addObject("pageTitle", pageTitle);
            mv.addObject("contentPage", "pages/admin/flight/list.jsp");
            mv.addObject("flights", flights);
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO while fecthing flight list : "+daoEx.getMessage());
            daoEx.printStackTrace();
        } catch (SQLException sqlEx){
            mv.addObject("error", "Error on SQL while fetching flight list : "+sqlEx.getMessage());
        } catch (Exception ex){
            mv.addObject("error", "Unexepted error : "+ex.getMessage());
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                               Create a flight                              */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/add")
    public ModelView showFlightForm(){
        ModelView mv = new ModelView("/WEB-INF/views/layout/admin-layout.jsp");
        mv.addObject("pageTitle", "");
        mv.addObject("contentPage", "pages/admin/flight/add-flight.jsp");

        try {
            List<Plane> planes = planeService.getAll();
            List<City> cities = cityService.getAll();
            
            mv.addObject("planes", planes);
            mv.addObject("cities", cities);
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO while fecthing flight list : "+daoEx.getMessage());
            daoEx.printStackTrace();
        } catch (SQLException sqlEx){
            mv.addObject("error", "Error on SQL while fetching flight list : "+sqlEx.getMessage());
        } catch (Exception ex){
            mv.addObject("error", "Unexepted error : "+ex.getMessage());
        }
        return mv;
    }

    @Post
    @Url("/flights")
    public ModelView addFlight(@RequestParam("flightArg") FlightArg fArg){
        if(fArg != null){
            ModelView mv = new ModelView();
            mv.setUrl("flights");
            try {
                Flight flight = flightService.injectValues(new Flight(), fArg);
                flightService.addFlight(flight);
                mv.setRedirect(true);
                mv.addObject("success", "Flight created successfully");
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO while fecthing flight list : "+daoEx.getMessage());
            } catch (SQLException sqlEx){
                mv.addObject("error", "Error on SQL while fetching flight list : "+sqlEx.getMessage());
            } catch (Exception ex){
                mv.addObject("error", "Unexepted error : "+ex.getMessage());
            }
            return mv;
        }else{
            ModelView mv = new ModelView("/flights/add");
            mv.addObject("error", "Information for flight is null");
            return mv;
        }
    }
}
