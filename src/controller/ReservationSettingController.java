package controller;

import java.sql.SQLException;

import exception.DaoException;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.handler.views.ModelView;
import model.ReservationSetting;
import service.ReservationSettingService;

@Controller

public class ReservationSettingController {

    private final ReservationSettingService reservationSettingService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public ReservationSettingController(){
        this.reservationSettingService = new ReservationSettingService();
    }
    
    /* -------------------------------------------------------------------------- */
    /*                              Show setting form                             */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/reservation-setting/add")
    public ModelView showReservationSetting(){
        ModelView mv = new ModelView("/WEB-INF/views/layout/admin-layout.jsp");
        mv.addObject("pageTitle", "");
        mv.addObject("contentPage", "pages/admin/reservation/setting.jsp");

        try {
            
            ReservationSetting r = reservationSettingService.getSetting();

            mv.addObject("setting", r);
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
    /*                            Manage update setting                           */
    /* -------------------------------------------------------------------------- */
    @Post
    @Url("/reservation-setting/add")
    public ModelView manageSetting(@RequestParam("setting") ReservationSetting arg){
        ModelView mv = new ModelView();
        mv.setUrl("add");
        if (arg != null) {
            try {
                reservationSettingService.update(arg);
                mv.setRedirect(true);
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
            mv.addObject("error", "Information for flight is null");
        }
        return mv;
    }
}
