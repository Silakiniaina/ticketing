package controller;

import lombok.Getter;
import lombok.Setter;
import mg.dash.mvc.annotation.Auth;
import mg.dash.mvc.annotation.Controller;
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
}
