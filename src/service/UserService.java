package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import error.AuthException;
import model.User;

public class UserService {
    
    private final UserDAO userDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public UserService(){
        this.userDAO = new UserDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*              Service for login a user with email and password              */
    /* -------------------------------------------------------------------------- */
    public User login(String email, String password, Connection c) throws AuthException, SQLException{
        return this.userDAO.login(email, password, c);
    }
}
