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
        return ok(index.render(signupForm));
    }

    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<User> filledForm = signupForm.bindFromRequest();

//        if(filledForm.hasErrors()) {
//            return badRequest(form.render(filledForm));
//        } else {
//            User created = filledForm.get();
//            return ok(summary.render(created));
//        }
        return ok(index.render(signupForm));
    }

  
}
