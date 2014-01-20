package controllers;

import models.User;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.data.*;
import utils.EncryptionUtil;
import views.html.*;


import static play.data.Form.form;

/**
 * User: Charles
 * Date: 5/5/13
 */

public class RecoverPassword extends MasterController {

    final static Form<User> userForm = form(User.class);
    final static Form<User> secureForm = form(User.class);

    public static Result index() {
        return ok( recoverPassword.render("", "", "", false, null, "", userForm, secureForm) );
    }

    public static Result isUser() {
        Form<User> filledForm = userForm.bindFromRequest();
        User user = User.findUserByName(filledForm.data().get("username"));

        if (user != null) {
            Http.Context.current().session().put("recover_user", user.username);
            return ok( recoverPassword.render(user.securityQuestionOne, user.securityQuestionTwo,user.securityQuestionThree, true, null, "", userForm, secureForm) );
        }
        flash("error", "Sorry, that user doesn't exist");
        return ok( index.render(form(User.class), form(User.class)));
    }

    public static Result secureQuestions() {
        Form<User> filledForm = secureForm.bindFromRequest();
        String userName = Http.Context.current().session().get("recover_user");
        User user = User.findUserByName(userName);
        String answerOne = filledForm.data().get("securityAnswerOne");
        String answerTwo = filledForm.data().get("securityAnswerTwo");
        String answerThree = filledForm.data().get("securityAnswerThree");

        try {
            if (answerOne.toLowerCase().equals(user.securityAnswerOne.toLowerCase()) &&
                    answerTwo.toLowerCase().equals(user.securityAnswerTwo.toLowerCase()) &&
                    answerThree.toLowerCase().equals(user.securityAnswerThree.toLowerCase())) {

                String passwordToReturn = new EncryptionUtil().decrypt(user.password);

                return ok(recoverPassword.render("", "", "", false, true, passwordToReturn, userForm, secureForm));
            }
        } catch (Exception e) {
            return internalServerError(recoverPassword.render("", "", "", false, false, "", userForm, secureForm));
        }

        return ok(recoverPassword.render("", "", "", false, false, "", userForm, secureForm));

    }

}
