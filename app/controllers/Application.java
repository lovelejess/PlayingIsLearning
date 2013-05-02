package controllers;

import enums.MessageLevel;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.*;
import play.mvc.*;
import play.data.*;

import utils.DataUtil;
import utils.EncryptionUtil;
import utils.StringUtil;
import views.html.*;

import models.*;

import static play.data.Form.form;

public class Application extends MasterController {

    /**
     * Defines a form wrapping the User class.
     */
    final static Form<User> loginForm = form(User.class);
    final static Form<User> registerForm = form(User.class);

    public String getTab() {
        return "index";
    }

    public static Result index() {
        return ok(index.render(loginForm, registerForm,  new Message("This application is in dev mode", MessageLevel.INFO)));
    }

    /**
     * Handle login.
     */
    public static Result login() {
        Form<User> filledForm = loginForm.bindFromRequest();
        String userName = filledForm.data().get("username");

        if(userName == null || userName.isEmpty()) {
            return ok(index.render(loginForm, registerForm, new Message("Please enter a username", MessageLevel.ERROR)));
        }

        if (User.isUsernameTaken(userName)) {
            if (User.getDecryptedPasswordForUser(userName).equals(filledForm.data().get("password"))) {
                return ok(landing.render(filledForm));
            }
        }

        return ok(index.render(loginForm, registerForm,  new Message("Your username or password is incorrect", MessageLevel.ERROR)));

    }

    /**
     * Handle registration.
     */
    public static Result register() {
        Form<User> filledForm = registerForm.bindFromRequest();

        if (User.isUsernameTaken(registerForm.data().get("username"))) {
            return ok(index.render(loginForm, registerForm,  new Message("That username is already taken", MessageLevel.ERROR)));
        }
        if (filledForm.data().get("username") == null || filledForm.data().get("username").isEmpty()) {
            return ok(index.render(loginForm, registerForm,  new Message("Please enter a username", MessageLevel.ERROR)));
        }
        if (!filledForm.data().get("repeatPassword").equals(filledForm.data().get("password")) || StringUtil.isEmpty(filledForm.data().get("repeatPassword")) || StringUtil.isEmpty(filledForm.data().get("password"))) {
            return ok(index.render(loginForm, registerForm,  new Message("Your passwords do not match", MessageLevel.ERROR)));
        }
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);
            EncryptionUtil encryptionUtil = new EncryptionUtil();
            User newUser = new User(filledForm.data().get("username"), encryptionUtil.encrypt(filledForm.data().get("password")));

            WriteResult<User, String> result = collection.insert(newUser);
        } catch (Exception e) {
            return internalServerError(index.render(loginForm, registerForm,  new Message("Unexpected error", MessageLevel.ERROR)));
        }

        return ok(landing.render(filledForm));

    }

}
