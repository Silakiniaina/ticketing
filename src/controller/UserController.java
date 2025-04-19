package controller;

import service.UserService;

public class UserController {

    private final UserService userService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public UserController(){
        this.userService = new UserService();
    }
}
