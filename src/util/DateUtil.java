package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    /* -------------------------------------------------------------------------- */
    /*            Convert a string into a Timestamp using Localdatetime           */
    /* -------------------------------------------------------------------------- */
    public static Timestamp convertStringToTimestamp(String str){
        return Timestamp.valueOf(LocalDateTime.parse(str));
    }

    /* -------------------------------------------------------------------------- */
    /*                    Convert a Timestamp into web datetime                   */
    /* -------------------------------------------------------------------------- */
    public static String convertTimestampToWebDatetime(Timestamp t){
        return convertLocalDatetimeToWebDatetime(LocalDateTime.parse(t.toString().replace(" ", "T")));
    }

    /* -------------------------------------------------------------------------- */
    /*             public static convert a Datetime into web datetime             */
    /* -------------------------------------------------------------------------- */
    public static String convertLocalDatetimeToWebDatetime(LocalDateTime l){
        return l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")).toString();
        
    }

    
}
