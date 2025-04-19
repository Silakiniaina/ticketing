package controller;

import mg.dash.mvc.handler.views.ModelView;
import service.UserService;

public class AuthController {

    private final UserService userService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public AuthController(){
        this.userService = new UserService();
    }
}
