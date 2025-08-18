package controller;

import java.sql.SQLException;
import java.util.List;

import dto.FlightPromotionArg;
import dto.UpdateArg;
import exception.DaoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mg.dash.mvc.annotation.Auth;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.handler.views.ModelView;
import model.FlightPromotion;
import model.TypeSeat;
import service.FlightPromotionService;
import service.TypeSeatService;

@Controller
@Auth
@Getter
@Setter
@AllArgsConstructor
public class FlightPromotionController {

    private final TypeSeatService typeSeatService;
    private final FlightPromotionService flightPromotionService;

    /* -------------------------------------------------------------------------- */
    /*                                  Construct                                 */
    /* -------------------------------------------------------------------------- */
    public FlightPromotionController(){
        this.typeSeatService = new TypeSeatService();
        this.flightPromotionService = new FlightPromotionService();
    }

    
    /* -------------------------------------------------------------------------- */
    /*                             Show promotion form                            */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/flight-promotions/add")
    public ModelView showFlightPromotionForm(@RequestParam("flight") UpdateArg updateArg){
        ModelView mv = new ModelView("/WEB-INF/views/layout/admin-layout.jsp");
        mv.addObject("pageTitle", "");
        mv.addObject("contentPage", "pages/admin/flight/promotion/add-promotion.jsp");

        try {
            List<TypeSeat> typeSeats = typeSeatService.getAll();
            List<FlightPromotion> promotions = flightPromotionService.getByFlight(updateArg.getId());
            
            mv.addObject("typeSeats", typeSeats);
            mv.addObject("promotions", promotions);
            mv.addObject("flightId", updateArg.getId());
        } catch (DaoException daoEx) {
            mv.addObject("error", "Error on DAO  : "+daoEx.getMessage());
            daoEx.printStackTrace();
        } catch (SQLException sqlEx){
            mv.addObject("error", "Error on SQL  : "+sqlEx.getMessage());
        } catch (Exception ex){
            mv.addObject("error", "Unexepted error : "+ex.getMessage());
        }
        return mv;
    }

    /* -------------------------------------------------------------------------- */
    /*                            Manage promotion add                            */
    /* -------------------------------------------------------------------------- */
    @Post
    @Url("/flight-promotions/add")
    public ModelView managePromotion(@RequestParam("promotion") FlightPromotionArg arg){
        ModelView mv = new ModelView();
        if (arg != null) {
            try {
                FlightPromotion prom = flightPromotionService.injectValues(new FlightPromotion(), arg);
                prom = flightPromotionService.insert(prom);
                mv.addObject("success", "Flight promotion created successfully");
                String url = "add?flight.id="+arg.getFlightId();
                mv.setUrl(url);
                mv.setRedirect(true);
                System.out.println("Redirecting to: " + mv.getUrl());
            } catch (NumberFormatException nfe) {
                mv.addObject("error", "Invalid flight ID format: " + nfe.getMessage());
                mv.setRedirect(false);
            } catch (DaoException daoEx) {
                mv.addObject("error", "Error on DAO  " + daoEx.getMessage());
                mv.setRedirect(false);
            } catch (SQLException sqlEx) {
                mv.addObject("error", "Error on SQL  " + sqlEx.getMessage());
                mv.setRedirect(false);
            } catch (Exception ex) {
                mv.addObject("error", "Unexpected error: " + ex.getMessage());
                mv.setRedirect(false);
            }
        } else {
            mv.setUrl("/ticketing/fights");
            mv.addObject("error", "Information for flight is null");
        }
        return mv;
    }
}