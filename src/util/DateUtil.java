package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateUtil {
    
    /* -------------------------------------------------------------------------- */
    /*            Convert a string into a Timestamp using Localdatetime           */
    /* -------------------------------------------------------------------------- */
    public static Timestamp convertStringToTimestamp(String str){
        return Timestamp.valueOf(LocalDateTime.parse(str));
    }
}
