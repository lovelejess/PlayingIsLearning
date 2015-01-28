package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

public class Account extends Controller {

    public static Result index() {
        return ok( index.render());
    }

    public static Result gog() {
        return ok( gog.render());
    }

    public static Result resources() {
        return ok( resources.render());
    }

    public static Result playGog(){
        return ok( playGog.render());
    }

    public static Result builds(){
        return ok( builds.render());
    }

    public static Result playGogInstruct(){
        return ok( playGogInstruct.render());
    }
    public static Result displayDecks(){
        return ok( displayDecks.render());
    }

}