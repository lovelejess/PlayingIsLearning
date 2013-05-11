package controllers;

import models.User;
import play.mvc.Result;
import play.data.*;
import views.html.*;

import static play.data.Form.form;

/**
 * User: Charles
 * Date: 5/5/13
 */


public class Survey extends MasterController {

    final static Form<User> surveyForm = form(User.class);

    public static Result index() {
        return ok( survey.render(surveyForm) );
    }

}
