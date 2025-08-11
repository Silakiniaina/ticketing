package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database{

    private static Connection activeConnection;

    /* -------------------------------------------------------------------------- */
    /*                    Get a static connection for database                    */
    /* -------------------------------------------------------------------------- */
    public static Connection getConnection() throws Exception{
        Connection c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/ticketing", "postgres", "DashDashGo2K23!!");
        }catch(Exception e){
            e.printStackTrace();
            throw e ;
        }
        return c;
    }

    /* -------------------------------------------------------------------------- */
    /*                Close all opened ressources in a dao request                */
    /* -------------------------------------------------------------------------- */
    public static void closeRessources(ResultSet rs, PreparedStatement prstm , Connection c, Boolean isNewConnection) throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(prstm != null){
            prstm.close();
        }
        if( c != null){
            if( isNewConnection != null && isNewConnection.booleanValue() == true){
                c.close();
            }
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                   Get the active connection on the server                  */
    /* -------------------------------------------------------------------------- */
    public static Connection getActiveConnection()throws Exception{
        if(activeConnection == null){
            activeConnection = getConnection();
        }
        return activeConnection;
    }

}