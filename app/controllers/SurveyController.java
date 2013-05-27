package controllers;

import models.*;
import net.vz.mongodb.jackson.JacksonDBCollection;
import utils.DataUtil;
import views.html.*;
import play.mvc.*;
import play.data.*;
import views.html.*;

import static play.data.Form.form;

/**
 * User: Charles
 * Date: 5/5/13
 */

public class SurveyController extends MasterController {

    final static Form<Survey> surveyForm = form(Survey.class);

    public static Result index() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        if(userSurvey == null) {
            userSurvey = new Survey(getLoggedInUser().getId());
            JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);
            collection.insert(userSurvey);
        }
        return ok( survey.render( surveyForm, userSurvey, getStage(userSurvey) ) );
    }

    public static Result stageOne() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();

        try {
            Boolean isParent = Boolean.parseBoolean(filledForm.data().get("isParent"));
            String relationshipToChild = filledForm.data().get("relationshipToChild");
            String age = filledForm.data().get("age");
            String zip = filledForm.data().get("zip");
            String childrenInCare = filledForm.data().get("childrenInCare");
            String childOne = filledForm.data().get("c1");
            String childTwo = filledForm.data().get("c2");
            String childThree = filledForm.data().get("c3");
            String childFour = filledForm.data().get("c4");
            String childFive = filledForm.data().get("c5");
            String childSix = filledForm.data().get("c6");
            String race = filledForm.data().get("race");
            String ethnicity = filledForm.data().get("ethnicity");
            String income = filledForm.data().get("income");
            Boolean isTwoParentHousehold = Boolean.parseBoolean(filledForm.data().get("isTwoParentHousehold"));
            Boolean isEnglishPrimaryLanguage = Boolean.parseBoolean(filledForm.data().get("isEnglishPrimaryLanguage"));
            String primaryLanguage = filledForm.data().get("primaryLanguage");

            userSurvey.isParent = isParent;
            userSurvey.relationshipToChild = relationshipToChild;
            userSurvey.age = Integer.parseInt(age);
            userSurvey.zip = Integer.parseInt(zip);
            userSurvey.childrenInCare = Integer.parseInt(childrenInCare);
            userSurvey.childAges.put("1", childOne);

            if(childTwo != null) userSurvey.childAges.put("2",childTwo);
            if(childThree != null) userSurvey.childAges.put("3",childThree);
            if(childFour != null) userSurvey.childAges.put("4",childFour);
            if(childFive != null) userSurvey.childAges.put("5",childFive);
            if(childSix != null) userSurvey.childAges.put("2",childSix);

            userSurvey.race = race;
            userSurvey.ethnicity = ethnicity;
            userSurvey.income = income;
            userSurvey.isTwoParentHousehold = isTwoParentHousehold;
            userSurvey.isEnglishPrimaryLanguage = isEnglishPrimaryLanguage;
            userSurvey.primaryLanguage = primaryLanguage;

        } catch(Exception e) {
            flash("warning", "You left a question unanswered.");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageOneComplete(true);
        saveUserSurvey(userSurvey);

        return redirect(routes.Entrance.index());
    }

    public static Result stageTwo() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();

        try{
            String playImportance = filledForm.data().get("playImportance");
            String playingRelatedToLearningKnowledge = filledForm.data().get("playingRelatedToLearningKnowledge");
            String motivatedToLearnAboutPlay = filledForm.data().get("motivatedToLearnAboutPlay");
            String hoursPerDayPlayingWithChildren = filledForm.data().get("hoursPerDayPlayingWithChildren");

            userSurvey.playImportance = Integer.parseInt(playImportance);
            userSurvey.playingRelatedToLearningKnowledge = Integer.parseInt(playingRelatedToLearningKnowledge);
            userSurvey.motivatedToLearnAboutPlay = Integer.parseInt(motivatedToLearnAboutPlay);
            userSurvey.hoursPerDayPlayingWithChildren = hoursPerDayPlayingWithChildren;
        }catch(Exception e){
            flash("warning", "You left a question unanswered.");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageTwoComplete(true);
        saveUserSurvey(userSurvey);

        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageThree() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();

        userSurvey.setIsStageThreeComplete(false);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageFour() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();

        try{
            String frequencyTakeChildrenToICM = filledForm.data().get("frequencyTakeChildrenToICM");
            String childsFavoriteExhibit = filledForm.data().get("childsFavoriteExhibit");
            String yourFavoriteExhibit = filledForm.data().get("yourFavoriteExhibit");

            userSurvey.frequencyTakeChildrenToICM = frequencyTakeChildrenToICM;
            userSurvey.childsFavoriteExhibit = childsFavoriteExhibit;
            userSurvey.yourFavoriteExhibit = yourFavoriteExhibit;
        }catch (Exception e) {
            flash("warning", "You left a question unanswered.");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageFourComplete(true);
        saveUserSurvey(userSurvey);

        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
    }

    public static Result stageFive() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();

        try{
            String howMuchHaveYouLearned = filledForm.data().get("howMuchHaveYouLearned");
            String howOftenDoYouPlay = filledForm.data().get("howOftenDoYouPlay");
            String howMuchFunDoYouHave = filledForm.data().get("howMuchFunDoYouHave");
            String isRecommend = filledForm.data().get("isRecommend");

            userSurvey.howMuchHaveYouLearned = Integer.parseInt(howMuchHaveYouLearned);
            userSurvey.howOftenDoYouPlay = howOftenDoYouPlay;
            userSurvey.howMuchFunDoYouHave = Integer.parseInt(howMuchFunDoYouHave);
            userSurvey.isRecommend = Boolean.parseBoolean(isRecommend);
        }catch (Exception e) {
            flash("warning", "You left a question unanswered.");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey)) );
        }
        userSurvey.setIsStageFiveComplete(true);
        saveUserSurvey(userSurvey);
        return redirect("/landing");
    }

    private static String getStage(Survey userSurvey) {
        if(userSurvey.getIsStageOneComplete() == null || !userSurvey.getIsStageOneComplete())
            return "one";
        if(userSurvey.getIsStageTwoComplete() == null || !userSurvey.getIsStageTwoComplete())
            return "two";
        if(userSurvey.getIsStageThreeComplete() == null || !userSurvey.getIsStageThreeComplete())
            return "three";
        if(userSurvey.getIsStageFourComplete() == null || !userSurvey.getIsStageFourComplete())
            return "four";
        if(userSurvey.getIsStageFiveComplete() == null || !userSurvey.getIsStageFiveComplete())
            return "five";
        return "done";
    }

    public static String getStageForLoggedInUser() {
        String isPrompted = Http.Context.current().session().get("surveyPrompt");
        if(isPrompted != null && isPrompted.equals("true")) {
            return "done";
        }

        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Http.Context.current().session().put("surveyPrompt", "true");

        if(userSurvey == null)
            return null;
        if(userSurvey.getIsStageOneComplete() == null || !userSurvey.getIsStageOneComplete())
            return "one";
        if(userSurvey.getIsStageTwoComplete() == null || !userSurvey.getIsStageTwoComplete())
            return "two";
        if(userSurvey.getIsStageThreeComplete() == null || !userSurvey.getIsStageThreeComplete())
            return "three";
        if(userSurvey.getIsStageFourComplete() == null || !userSurvey.getIsStageFourComplete())
            return "four";
        if(userSurvey.getIsStageFiveComplete() == null || !userSurvey.getIsStageFiveComplete())
            return "five";

        return "done";
    }

    private static void saveUserSurvey(Survey userSurvey) {
        JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);
        collection.save(userSurvey);
    }

}
