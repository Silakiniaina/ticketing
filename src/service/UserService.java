package service;

import dao.UserDAO;

public class UserService {
    
    private final UserDAO userDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public UserService(){
        this.userDAO = new UserDAO();
    }
}
