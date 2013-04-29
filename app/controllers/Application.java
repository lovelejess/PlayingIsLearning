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
    final static Form<User> loginForm = form(User.class);
    final static Form<User> registerForm = form(User.class);


    public static Result index() {
        return ok(index.render(loginForm, registerForm, null));
    }

    /**
     * Handle login.
     */
    public static Result login() {
        Form<User> filledForm = loginForm.bindFromRequest();

        if(filledForm.data().get("username") == null || filledForm.data().get("username").isEmpty()) {
            return ok(index.render(loginForm, registerForm, "Error"));
        }

        if(true) {
            return ok(landing.render(filledForm));
        }
        else {
            return ok(index.render(loginForm,registerForm, null));
        }
    }

    /**
     * Handle registration.
     */
    public static Result register() {
        Form<User> filledForm = registerForm.bindFromRequest();

        if (User.isUsernameTaken(registerForm.data().get("username"))) {
            return internalServerError(landing.render(filledForm));
        }

        if (true) {
            return ok(landing.render(filledForm));
        } else {
            return ok(index.render(loginForm, registerForm, null));
        }


    }


}
