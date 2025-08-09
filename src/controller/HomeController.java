package controller;

import mg.dash.mvc.annotation.Controller;
import mg.dash.mvc.annotation.Get;
import mg.dash.mvc.annotation.Url;
import mg.dash.mvc.handler.views.ModelView;

@Controller
public class HomeController{

    @Get
    @Url("/home")
    public ModelView showHomePage(){
        ModelView mv = new ModelView("/WEB-INF/views/layout/main-layout.jsp");
        mv.addObject("pageTitle", "Home");
        mv.addObject("contentPage", "pages/home.jsp");

        return mv;
    }
}