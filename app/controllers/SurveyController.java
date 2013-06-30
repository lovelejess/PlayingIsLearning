package controllers;

import models.*;
import net.vz.mongodb.jackson.JacksonDBCollection;
import utils.DataUtil;
import views.html.*;
import play.mvc.*;
import play.data.*;
import views.html.*;

import java.util.List;

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
        return ok( survey.render( surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
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
            if(childSix != null) userSurvey.childAges.put("6",childSix);

            userSurvey.race = race;
            userSurvey.ethnicity = ethnicity;
            userSurvey.income = income;
            userSurvey.isTwoParentHousehold = isTwoParentHousehold;
            userSurvey.isEnglishPrimaryLanguage = isEnglishPrimaryLanguage;
            userSurvey.primaryLanguage = primaryLanguage;

        } catch(Exception e) {
            flash("warning", "You left a question unanswered.");
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
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
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
        }
        userSurvey.setIsStageTwoComplete(true);
        saveUserSurvey(userSurvey);

        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
    }

    public static Result stageThree() {
        Survey userSurvey = Survey.findByUser(getLoggedInUser());
        Form<Survey> filledForm = surveyForm.bindFromRequest();
        Integer childAge = userSurvey.getNextSurveyAge();

        String howOftenReadToChild = filledForm.data().get("howOftenReadToChild");
        String howOftenPlayGames = filledForm.data().get("howOftenPlayGames");
        String hoursPerDayPlaying = filledForm.data().get("hoursPerDayPlaying");
        String whoPlayWithRanking_alone = filledForm.data().get("whoPlayWithRanking_alone");
        String whoPlayWithRanking_friends = filledForm.data().get("whoPlayWithRanking_friends");
        String whoPlayWithRanking_siblings = filledForm.data().get("whoPlayWithRanking_siblings");
        String whoPlayWithRanking_adults = filledForm.data().get("whoPlayWithRanking_adults");
        String topThreePlayTimeActivities_pretend = filledForm.data().get("topThreePlayTimeActivities_pretend");
        String topThreePlayTimeActivities_physical = filledForm.data().get("topThreePlayTimeActivities_physical");
        String topThreePlayTimeActivities_videogames = filledForm.data().get("topThreePlayTimeActivities_videogames");
        String topThreePlayTimeActivities_outside = filledForm.data().get("topThreePlayTimeActivities_outside");
        String topThreePlayTimeActivities_bookspuzzles = filledForm.data().get("topThreePlayTimeActivities_bookspuzzles");
        String topThreePlayTimeActivities_art = filledForm.data().get("topThreePlayTimeActivities_art");
        String topThreePlayTimeActivities_gog = filledForm.data().get("topThreePlayTimeActivities_gog");
        String topThreePlayTimeActivities_cardsorboardgames = filledForm.data().get("topThreePlayTimeActivities_cardsorboardgames");
        String topThreePlayTimeActivities_objects = filledForm.data().get("topThreePlayTimeActivities_objects");
        String topThreePlayTimeActivities_other = filledForm.data().get("topThreePlayTimeActivities_other");

        if(howOftenReadToChild != null) userSurvey.howOftenReadToChild.put("howOftenReadToChild:"+childAge,howOftenReadToChild);
        if(howOftenPlayGames != null) userSurvey.howOftenPlayGames.put("howOftenPlayGames:"+childAge,howOftenPlayGames);
        if(hoursPerDayPlaying != null) userSurvey.hoursPerDayPlaying.put("hoursPerDayPlaying:"+childAge,hoursPerDayPlaying);

        if(whoPlayWithRanking_alone != null) userSurvey.whoPlayWithRanking.put("whoPlayWithRanking_alone:"+childAge,whoPlayWithRanking_alone);
        if(whoPlayWithRanking_friends != null) userSurvey.whoPlayWithRanking.put("whoPlayWithRanking_friends:"+childAge,whoPlayWithRanking_friends);
        if(whoPlayWithRanking_siblings != null) userSurvey.whoPlayWithRanking.put("whoPlayWithRanking_siblings:"+childAge,whoPlayWithRanking_siblings);
        if(whoPlayWithRanking_adults != null) userSurvey.whoPlayWithRanking.put("whoPlayWithRanking_adults:"+childAge,whoPlayWithRanking_adults);

        if(topThreePlayTimeActivities_pretend != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_pretend:"+childAge,topThreePlayTimeActivities_pretend);
        if(topThreePlayTimeActivities_physical != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_physical:"+childAge,topThreePlayTimeActivities_physical);
        if(topThreePlayTimeActivities_videogames != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_videogames:"+childAge,topThreePlayTimeActivities_videogames);
        if(topThreePlayTimeActivities_outside != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_outside:"+childAge,topThreePlayTimeActivities_outside);
        if(topThreePlayTimeActivities_bookspuzzles != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_bookspuzzles:"+childAge,topThreePlayTimeActivities_bookspuzzles);
        if(topThreePlayTimeActivities_art != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_art:"+childAge,topThreePlayTimeActivities_art);
        if(topThreePlayTimeActivities_gog != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_gog:"+childAge,topThreePlayTimeActivities_gog);
        if(topThreePlayTimeActivities_cardsorboardgames != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_cardsorboardgames:"+childAge,topThreePlayTimeActivities_cardsorboardgames);
        if(topThreePlayTimeActivities_objects != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_objects:"+childAge,topThreePlayTimeActivities_objects);
        if(topThreePlayTimeActivities_other != null) userSurvey.topThreePlayTimeActivities.put("topThreePlayTimeActivities_other:"+childAge,topThreePlayTimeActivities_other);

        if(userSurvey.getNextSurveyAge() == null)
            userSurvey.setIsStageThreeComplete(true);

        userSurvey.agesComplete.put(childAge.toString(),childAge);
        saveUserSurvey(userSurvey);
        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
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
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
        }
        userSurvey.setIsStageFourComplete(true);
        saveUserSurvey(userSurvey);

        return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
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
            return ok( survey.render(surveyForm, userSurvey, getStage(userSurvey), userSurvey.getNextSurveyAge()) );
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

    public static List<Integer> getChildAgesList() {
        return Survey.findByUser(getLoggedInUser()).getChildAgesList();
    }

}
