package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;

import static play.data.Form.form;

public class Application extends Controller {

    /**
     * Defines a form wrapping the User class.
     */
    final static Form<User> signupForm = form(User.class, User.All.class);


    public static Result index() {
        return ok(index.render(signupForm, null));
    }

    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<User> filledForm = signupForm.bindFromRequest();
        System.out.println(filledForm);

        if(filledForm.data().get("username") == null || filledForm.data().get("username").isEmpty()) {
            return ok(index.render(signupForm, "Error"));
        }

        if(true) {
            return ok(landing.render(filledForm));
        }
        else {
            return ok(index.render(signupForm, null));
        }


    }

  
}
