package model.shared;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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