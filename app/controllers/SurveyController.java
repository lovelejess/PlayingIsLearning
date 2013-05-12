package controllers;

import models.*;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.mvc.Result;
import play.data.*;
import utils.DataUtil;
import views.html.*;

import static play.data.Form.form;

/**
 * User: Charles
 * Date: 5/5/13
 */

public class SurveyController extends MasterController {

    final static Form<User> surveyForm = form(User.class);

    public static Result index() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            userSurvey = new Survey(getLoggedInUser().getId());
            JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);
            collection.insert(userSurvey);
        }
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageOne() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            flash("error","");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageOneComplete(true);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageTwo() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            flash("error","");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageTwoComplete(true);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageThree() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            flash("error","");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageThreeComplete(true);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageFour() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            flash("error","");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageFourComplete(true);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageFive() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            flash("error","");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageFiveComplete(true);
        saveUserSurvey(userSurvey);
        return ok( landing.render(getLoggedInUser()) );
    }

    private static String getStage(Survey userSurvey) {
        if(userSurvey.getIsStageOneComplete() == null || !userSurvey.getIsStageOneComplete()) {
            return "one";
        }
        if(userSurvey.getIsStageTwoComplete() == null || !userSurvey.getIsStageTwoComplete()) {
            return "two";
        }
        if(userSurvey.getIsStageThreeComplete() == null || !userSurvey.getIsStageThreeComplete()) {
            return "three";
        }
        if(userSurvey.getIsStageFourComplete() == null || !userSurvey.getIsStageFourComplete()) {
            return "four";
        }
        if(userSurvey.getIsStageFiveComplete() == null || !userSurvey.getIsStageFiveComplete()) {
            return "five";
        }
        return "done";
    }

    public static String getStageForLoggedInUser() {
        Survey userSurvey = Survey.findByUserId(getLoggedInUser().getId());
        if(userSurvey == null) {
            return null;
        }
        if(userSurvey.getIsStageOneComplete() == null || !userSurvey.getIsStageOneComplete()) {
            return "one";
        }
        if(userSurvey.getIsStageTwoComplete() == null || !userSurvey.getIsStageTwoComplete()) {
            return "two";
        }
        if(userSurvey.getIsStageThreeComplete() == null || !userSurvey.getIsStageThreeComplete()) {
            return "three";
        }
        if(userSurvey.getIsStageFourComplete() == null || !userSurvey.getIsStageFourComplete()) {
            return "four";
        }
        if(userSurvey.getIsStageFiveComplete() == null || !userSurvey.getIsStageFiveComplete()) {
            return "five";
        }
        return "done";
    }

    private static void saveUserSurvey(Survey userSurvey) {
        JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);
        collection.save(userSurvey);
    }

}
