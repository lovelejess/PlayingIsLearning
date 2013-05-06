package controllers;

import enums.MessageLevelEnum;
import models.Message;
import models.User;
import play.data.Form;
import play.mvc.Result;
import play.data.*;
import utils.DataUtil;
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
            return ok( recoverPassword.render(user.securityQuestionOne, user.securityQuestionTwo,user.securityQuestionThree, true, null, "", userForm, secureForm) );
        }

        return ok( index.render(form(User.class), form(User.class), new Message("Sorry, that user doesn't exist", MessageLevelEnum.ERROR)) );


    }

    public static Result secureQuestions() {
        Form<User> filledForm = secureForm.bindFromRequest();

        String answerOne = filledForm.data().get("securityAnswerOne");
        String answerTwo = filledForm.data().get("securityAnswerTwo");
        String answerThree = filledForm.data().get("securityAnswerThree");

        if(answerOne.equals("gdsfg") && answerTwo.equals("sdg") && answerThree.equals("sdgdfsg") ) {
            return ok( recoverPassword.render("", "","",false, true, "", userForm, secureForm) );
        }

        return ok(recoverPassword.render("", "", "", false, false, "", userForm, secureForm));

    }

}
