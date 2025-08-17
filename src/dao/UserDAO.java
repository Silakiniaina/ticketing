package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.AuthException;
import model.User;
import util.Database;

public class UserDAO {
    /* -------------------------------------------------------------------------- */
    /*          Function to login for user with their email and password          */
    /* -------------------------------------------------------------------------- */
    public User login(String email, String password, Connection c) throws AuthException, SQLException {
        User connected = null; 
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null;
        String query = "SELECT * FROM v_users WHERE email = ? AND password = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }

            prstm = c.prepareStatement(query);
            prstm.setString(1, email);
            prstm.setString(2, password);

            rs = prstm.executeQuery();
            if (rs.next()) {
                connected = new User();
                connected.setId(rs.getInt("id"));
                connected.setLastName(rs.getString("last_name"));
                connected.setFirstName(rs.getString("first_name"));
                connected.setBirthDate(rs.getDate("birth_date"));
                connected.setEmail(rs.getString("email"));
                connected.setRole(rs.getString("role"));
            }
            return connected;
        } catch (Exception e) {
            throw new AuthException(email, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                           Get a user by its id                             */
    /* -------------------------------------------------------------------------- */
    public User getById(Connection c, int id) throws AuthException, SQLException {
        User result = null;
        boolean isNewConnection = false;
        PreparedStatement prstm = null;
        ResultSet rs = null;
        String query = "SELECT * FROM v_users WHERE id = ?";
        try {
            if (c == null) {
                c = Database.getActiveConnection();
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, id);
            rs = prstm.executeQuery();
            if (rs.next()) {
                result = new User();
                result.setId(rs.getInt("id"));
                result.setLastName(rs.getString("last_name"));
                result.setFirstName(rs.getString("first_name"));
                result.setBirthDate(rs.getDate("birth_date"));
                result.setEmail(rs.getString("email"));
                result.setRole(rs.getString("role"));
            }
            return result;
        } catch (Exception e) {
            throw new AuthException("ID: " + id, e.getMessage());
        } finally {
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}