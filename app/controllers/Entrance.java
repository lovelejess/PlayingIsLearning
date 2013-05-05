package controllers;

import enums.MessageLevelEnum;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.mvc.*;
import play.data.*;
import views.html.*;


import utils.DataUtil;
import utils.EncryptionUtil;
import utils.StringUtil;

import models.*;

import static play.data.Form.form;

public class Entrance extends MasterController {

    /**
     * Defines a form wrapping the User class.
     */
    final static Form<User> loginForm = form(User.class);
    final static Form<User> registerForm = form(User.class);
    final static Form<User> securityForm = form(User.class);


    public static Result index() {
        return ok( index.render(loginForm, registerForm, new Message("This application is in dev mode", MessageLevelEnum.INFO)) );
    }

    /**
     * Handle login.
     */
    public static Result login() {
        Form<User> filledForm = loginForm.bindFromRequest();
        String userName = filledForm.data().get("username");

        if(userName == null || userName.isEmpty()) {
            return badRequest( index.render(filledForm, registerForm, new Message("Please enter a username", MessageLevelEnum.ERROR)) );
        }

        if (User.isUsernameTaken(userName)) {
            if (User.getDecryptedPasswordForUser(userName).equals(filledForm.data().get("password"))) {
                User user = User.findUserByName(userName);
                return ok( landing.render(user) );
            }
        }

        return badRequest( index.render(filledForm, registerForm,  new Message("Your username or password is incorrect", MessageLevelEnum.ERROR)) );

    }

    /**
     * Handle registration.
     */
    public static Result register() {
        Form<User> filledForm = registerForm.bindFromRequest();
        User newUser;

        if (User.isUsernameTaken(filledForm.data().get("username"))) {
            return badRequest( index.render(loginForm, filledForm,  new Message("That username is already taken", MessageLevelEnum.ERROR)) );
        }
        if (filledForm.data().get("username") == null || filledForm.data().get("username").isEmpty()) {
            return badRequest( index.render(loginForm, filledForm,  new Message("Please enter a username", MessageLevelEnum.ERROR)) );
        }
        if (!filledForm.data().get("repeatPassword").equals(filledForm.data().get("password")) || StringUtil.isEmpty(filledForm.data().get("repeatPassword")) || StringUtil.isEmpty(filledForm.data().get("password"))) {
            return badRequest( index.render(loginForm, filledForm,  new Message("Your passwords do not match", MessageLevelEnum.ERROR)) );
        }
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);
            EncryptionUtil encryptionUtil = new EncryptionUtil();
            newUser = new User(filledForm.data().get("username"), encryptionUtil.encrypt(filledForm.data().get("password")));

            WriteResult<User, String> result = collection.insert(newUser);

            Http.Context.current().session().put("user", result.getSavedId());

        } catch (Exception e) {
            return internalServerError( index.render(loginForm, filledForm,  new Message("Unexpected error", MessageLevelEnum.ERROR)) );
        }

        return ok( security.render(securityForm, newUser.getId(), null));

    }


    /**
     * Handle security questions.
     */
    public static Result security() {
        Form<User> filledForm = securityForm.bindFromRequest();
        User user = (User)DataUtil.getEntityById("users", User.class, Http.Context.current().session().get("user"));

        try {

            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

            user.securityAnswerOne = filledForm.data().get("securityAnswerOne");
            user.securityAnswerTwo = filledForm.data().get("securityAnswerTwo");
            user.securityAnswerThree = filledForm.data().get("securityAnswerThree");
            user.securityQuestionOne = filledForm.data().get("securityQuestionOne");
            user.securityQuestionTwo = filledForm.data().get("securityQuestionTwo");
            user.securityQuestionThree = filledForm.data().get("securityQuestionThree");

            collection.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError( index.render(loginForm, filledForm,  new Message("Unexpected error:" + e.getMessage(), MessageLevelEnum.ERROR)) );
        }

        return ok( landing.render(user));

    }


}
