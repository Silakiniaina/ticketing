package controller;

import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.handler.views.ModelView;
import service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public AuthController(){
        this.userService = new UserService();
    }

    /* -------------------------------------------------------------------------- */
    /*                           Display the login page                           */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/auth")
    public ModelView getAuthModelView(){
        return new ModelView("/WEB-INF/views/users/auth.jsp");
    }
}
