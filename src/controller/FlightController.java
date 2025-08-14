package controller;

import java.sql.SQLException;
import java.util.List;

import dto.FlightArg;
import dto.UpdateArg;
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
    @Url("/flights/add")
    public ModelView addFlight(@RequestParam("flightArg") FlightArg fArg) {
        ModelView mv = new ModelView();
        mv.setUrl("add");
        if (fArg != null) {
            try {
                Flight flight = flightService.injectValues(new Flight(), fArg);
                if (fArg.getId() != 0) {
                    flightService.updateFlight(flight);
                    mv.addObject("success", "Flight updated successfully");
                } else {
                    flightService.addFlight(flight);
                    mv.addObject("success", "Flight created successfully");
                }
                mv.setRedirect(true);
                System.out.println("Redirecting to: " + mv.getUrl());
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid flight ID format: " + nfe.getMessage());
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO while fetching flight list: " + daoEx.getMessage());
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL while fetching flight list: " + sqlEx.getMessage());
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
            }
        } else {
            mv.addObject("error", "Information for flight is null");
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                                Update flight                               */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/update")
    public ModelView showUpdateForm(@RequestParam("updateArg") UpdateArg updateArg){
        ModelView mv = new ModelView("/flights/add");
        try {
            System.out.println(updateArg);
            if(updateArg == null){
                throw new Exception("Cannot update a flight with id null");
            }
            Flight f = flightService.getById(updateArg.getId());
            mv.addObject("flight", f);
        } catch (Exception e) {
            mv.addObject("error", e.getMessage());
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                               Delete a flight                              */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flights/delete")
    public ModelView deleteFlight(@RequestParam("flight") UpdateArg f){
        ModelView mv = new ModelView();
        mv.setUrl("/ticketing/flights");
        if (f != null) {
            try {
                if (f.getId() != 0) {
                    flightService.deleteFlight(flightService.getById(f.getId()));
                } else{
                    throw new Exception("Flight to delete not found");
                }
                mv.setRedirect(true);
                System.out.println("Redirecting to: " + mv.getUrl());
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid flight ID format: " + nfe.getMessage());
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO while deleting flight : " + daoEx.getMessage());
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL while deleting flight : " + sqlEx.getMessage());
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
            }
        } else {
            mv.addObject("error", "Information for flight is null");
        }
        return mv;
    }
}
