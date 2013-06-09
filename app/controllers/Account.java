package controllers;

import com.mongodb.MongoException;
import enums.PassportTypeEnum;
import models.Passport;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.mvc.*;
import play.data.*;
import utils.DataUtil;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * User: Charles
 * Date: 5/6/13
 */
public class Account extends MasterController {

    final static Form<Passport> passportForm = form(Passport.class);

    public static Result index() {
        return ok(landing.render(getLoggedInUser(),passportForm));
    }

    public static Result createPassport() {
        if(!DataUtil.isDatabase()) {
            flash("error", "Our database is currently down. Please contact a system administrator.");
            return ok(landing.render(getLoggedInUser(),passportForm));
        }
        Form<Passport> filledForm = passportForm.bindFromRequest();
        String passportName = filledForm.data().get("passportName");
        PassportTypeEnum passportType = PassportTypeEnum.valueOf(filledForm.data().get("type"));

        if(passportName == null || passportName.isEmpty()) {
            flash("error", "Please enter a passport name");
            return ok(landing.render(getLoggedInUser(), passportForm));
        }

        if(!Passport.isPassportNameUniqueForUser(getLoggedInUser().getId(),passportName)) {
            flash("error", "You have already created a passport with this name. Please choose a different name.");
            return ok(landing.render(getLoggedInUser(), passportForm));
        }

        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            Passport newPassport = new Passport(passportName,MasterController.getLoggedInUser().getId(),passportType);

            WriteResult<Passport, String> result = collection.insert(newPassport);

        } catch (Exception e) {
            flash("error", "Unexpected error: " + e.toString());
            return internalServerError(landing.render(getLoggedInUser(), passportForm));
        }

        return redirect("/landing/passport/"+passportName);
    }

    public static List<Passport> getUserPassports() {
        List<Passport> userPassports = new ArrayList<Passport>();
        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("userId", MasterController.getLoggedInUser().getId()));

            while(cursorDoc.hasNext()) {
                userPassports.add((Passport)cursorDoc.next());
            }

            return userPassports;

        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Result usePassport(String passportName) {
        if(!DataUtil.isDatabase()) {
            flash("error", "Our database is currently down. Please contact a system administrator.");
            return ok(landing.render(getLoggedInUser(),passportForm));
        }

        Http.Context.current().session().put("passport", passportName);

        return ok(landing.render(getLoggedInUser(), passportForm));
    }

    public static Boolean isPassportSelected() {
        String passport = Http.Context.current().session().get("passport");
        if(passport == null || passport.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public static String getSelectedPassport() {
        String passport = Http.Context.current().session().get("passport");
        if(passport != null && !passport.isEmpty()) {
            return passport;
        }
        return null;
    }



}