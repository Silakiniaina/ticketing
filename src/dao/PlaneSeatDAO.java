package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;
import lombok.Getter;
import lombok.Setter;
import model.Plane;
import model.PlaneSeat;
import util.Database;

@Getter
@Setter
public class PlaneSeatDAO {

    private TypeSeatDAO typeSeatDAO;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public PlaneSeatDAO(){
        this.typeSeatDAO = new TypeSeatDAO();
    }


    /* -------------------------------------------------------------------------- */
    /*                             Get a plane's seats                            */
    /* -------------------------------------------------------------------------- */
    public List<PlaneSeat> getByPlaneId(Connection c, Plane plane)throws DaoException, SQLException{
        List<PlaneSeat> seats = new ArrayList<>();
        boolean isNewConnection = false;
        PreparedStatement prstm = null; 
        ResultSet rs = null; 
        String query = "SELECT * FROM plane_seat WHERE plane_id = ?";
        try {
            if(c == null){
                c = Database.getActiveConnection(); 
                isNewConnection = true;
            }
            prstm = c.prepareStatement(query);
            prstm.setInt(1, plane.getId());
            rs = prstm.executeQuery();
            while(rs.next()){
                PlaneSeat p = new PlaneSeat();
                p.setTypeSeat(typeSeatDAO.getById(c, rs.getInt("type_seat_id")));
                p.setPlane(plane);
                p.setQuantity(rs.getInt("quantity"));
                seats.add(p);
            }
            return seats;
        } catch (Exception e) {
            throw new DaoException(query, e.getMessage());
        }finally{
            Database.closeRessources(rs, prstm, c, Boolean.valueOf(isNewConnection));
        }
    }
}