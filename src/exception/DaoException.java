package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DaoException extends Exception{

    private String query;
    private String superMessage;

    /* -------------------------------------------------------------------------- */
    /*                                  Override                                  */
    /* -------------------------------------------------------------------------- */
    @Override
    public String getMessage() {
        return "Error on DAO for query : "+this.getQuery()+" \n\t: "+this.getSuperMessage();
    }
}
