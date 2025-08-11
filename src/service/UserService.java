package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import exception.AuthException;
import model.User;
import util.Database;

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
    public User login(String email, String password) throws AuthException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return this.userDAO.login(email, password, c);
    }
}
