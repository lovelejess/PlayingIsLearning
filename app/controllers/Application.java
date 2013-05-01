package controllers;

import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.*;
import play.mvc.*;
import play.data.*;

import utils.DataUtil;
import utils.EncryptionUtil;
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
        String userName = registerForm.data().get("username");

        if(userName == null || userName.isEmpty()) {
            return ok(index.render(loginForm, registerForm, "Error"));
        }

        if (User.isUsernameTaken(userName)) {
            if (User.getEncryptedPasswordForUser(userName).equals(registerForm.data().get("password"))) {
                return ok(landing.render(filledForm));
            }
        }

        return ok(index.render(loginForm, registerForm, "Error"));

    }

    /**
     * Handle registration.
     */
    public static Result register() {
        Form<User> filledForm = registerForm.bindFromRequest();

        if (User.isUsernameTaken(registerForm.data().get("username"))) {
            return ok(index.render(loginForm, registerForm, "Error"));
        }
        if (filledForm.data().get("username") == null || filledForm.data().get("username").isEmpty()) {
            return ok(index.render(loginForm, registerForm, "Error"));
        }
        if (!filledForm.data().get("repeatPassword").equals(filledForm.data().get("password"))) {
            return ok(index.render(loginForm, registerForm, "Error"));
        }
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);
            EncryptionUtil encryptionUtil = new EncryptionUtil();
            User newUser = new User(filledForm.data().get("username"), encryptionUtil.encrypt(filledForm.data().get("password")), filledForm.data().get("email"), filledForm.data().get("realname"));

            WriteResult<User, String> result = collection.insert(newUser);
        } catch (Exception e) {
            return internalServerError(index.render(loginForm, registerForm, "Error"));
        }

        return ok(landing.render(filledForm));

    }

}
