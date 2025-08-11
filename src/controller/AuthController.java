package controller;

import exception.AuthException;
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
        return new ModelView("/WEB-INF/views/pages/auth.jsp");
    }

    /* -------------------------------------------------------------------------- */
    /*                        Manage user authentification                        */
    /* -------------------------------------------------------------------------- */
    @Post
    @Url("/auth")
    public ModelView auth(@RequestParam("user") User userAuth){
        ModelView mv = new ModelView();
        try {
            User connected = this.userService.login(userAuth.getEmail(), userAuth.getPassword());
            if(connected != null){
                session.setUser(connected);
                session.setUserRoles(connected.getUserRoles());
                mv.addObject("pageTitle", "Home");
                if(connected.getRole().equals("CLIENT")){
                    mv.setUrl("/WEB-INF/views/layout/client-layout.jsp");
                    mv.addObject("contentPage", "pages/client/home.jsp");
                }else if(connected.getRole().equals("MANAGER")){
                    mv.setUrl("/WEB-INF/views/layout/admin-layout.jsp");
                    mv.addObject("contentPage", "pages/admin/home.jsp");
                }
                return mv;
            }else{
                throw new AuthException(userAuth.getEmail(), "User not found , please verify your password or the information that you provided");
            }
        } catch (Exception e) {
            mv.setUrl("/WEB-INF/views/pages/auth.jsp");
            mv.addObject("error", e.getMessage());
            return mv;
        }
    }
}
