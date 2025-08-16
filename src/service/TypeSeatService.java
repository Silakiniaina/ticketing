package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.TypeSeatDAO;
import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import model.TypeSeat;
import util.Database;

@Getter
@Setter
public class TypeSeatService {
    
    private final TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public TypeSeatService(){
        this.typeSeatDAO = new TypeSeatDAO();
    }

    /* -------------------------------------------------------------------------- */
    /*                              Get all type seat                             */
    /* -------------------------------------------------------------------------- */
    public List<TypeSeat> getAll() throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return typeSeatDAO.getAll(c);
    }

    /* -------------------------------------------------------------------------- */
    /*                          Get a type seat by its id                         */
    /* -------------------------------------------------------------------------- */
    public TypeSeat getById(int id) throws DaoException, SQLException, Exception{
        Connection c = Database.getActiveConnection();
        return typeSeatDAO.getById(c, id);
    }
}

