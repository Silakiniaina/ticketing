package controller;

import java.sql.SQLException;

import error.AuthException;
import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Post;
import mg.dash.mvc.annotation.RequestParam;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.controller.MySession;
import mg.dash.mvc.handler.views.ModelView;
import model.User;
import service.UserService;

@Controller
public class AuthController {

    MySession session;
    private final UserService userService;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public AuthController(){
        this.userService = new UserService();
    }

    /* -------------------------------------------------------------------------- */
    /*                           Display the login page                           */
    /* -------------------------------------------------------------------------- */
    @Get
    @Url("/auth")
    public ModelView getAuthModelView(){
        return new ModelView("/WEB-INF/views/auth.jsp");
    }

    /* -------------------------------------------------------------------------- */
    /*                        Manage user authentification                        */
    /* -------------------------------------------------------------------------- */
    @Post
    @Url("/auth")
    public ModelView auth(@RequestParam("user") User userAuth){
        ModelView mv = new ModelView();
        try {
            User connected = this.userService.login(userAuth.getEmail(), userAuth.getPassword(), null);
            if(connected != null){
                session.setUser(connected);
                session.setUserRoles(connected.getUserRoles());
                if(connected.getRole().equals("CLIENT")){
                    mv.setUrl("/WEB-INF/views/users/home.jsp");
                }else if(connected.getRole().equals("MANAGER")){
                    mv.setUrl("/WEB-INF/views/managers/home.jsp");
                }
            }else{
                throw new AuthException(userAuth.getEmail(), "User not found , please verify your password or the information that you provided");
            }
        } catch (AuthException e) {
            mv.setUrl("/WEB-INF/views/users/auth.jsp");
            mv.addObject("error", e.getMessage());
        } catch (SQLException e){
            mv.setUrl("/WEB-INF/views/users/auth.jsp");
            mv.addObject("error", e.getMessage());
        } finally{
            return mv;
        }
    }
}
