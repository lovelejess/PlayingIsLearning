package controllers;

import play.mvc.Result;
import play.data.*;
import views.html.*;

/**
 * User: Charles
 * Date: 5/5/13
 */


public class Survey extends MasterController {

    public static Result index() {
        return ok( survey.render() );
    }

}
