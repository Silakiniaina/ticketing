package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database{

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
}