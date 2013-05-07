package controllers;

import play.mvc.*;
import views.html.*;
import play.mvc.Result;

/**
 * User: Charles
 * Date: 5/6/13
 */
public class Account extends MasterController {

    public static Result index() {
        return ok(landing.render(getLoggedInUser()));
    }

}