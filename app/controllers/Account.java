package controllers;

import com.mongodb.MongoException;
import enums.PassportTypeEnum;
import models.Passport;
import org.mongojack.DBQuery;
import play.mvc.*;
import play.data.*;
import utils.DataUtil;
import views.html.*;
import org.mongojack.JacksonDBCollection;

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
            String childAge = filledForm.data().get("childAge");
            if(childAge == null || childAge.isEmpty()) {
                flash("error", "Please choose a child's age to use with this passport");
                return ok(landing.render(getLoggedInUser(), passportForm));
            }
            else {
                Integer childAgeInt = Integer.parseInt(childAge);
                newPassport.setChildAge(childAgeInt);
            }

            collection.insert(newPassport);

        } catch (Exception e) {
            flash("error", "Unexpected error: " + e.toString());
            return internalServerError(landing.render(getLoggedInUser(), passportForm));
        }

        return redirect("/landing/passport/"+passportName);
    }

    public static List<Passport> getUserPassports() {
        List<Passport> userPassports = new ArrayList<Passport>();
        try {
            return DataUtil.getCollection("passports", Passport.class).find(DBQuery.is("userId", MasterController.getLoggedInUser().getId())).toArray();

        } catch (MongoException e) {
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
        return (passport != null && !passport.isEmpty());
    }

    public static String getSelectedPassportName() {
        String passport = Http.Context.current().session().get("passport");
        if(passport != null && !passport.isEmpty())
            return passport;

        return null;
    }

    public static Boolean isSelectedPassportUnder3() {
        String passportName = Http.Context.current().session().get("passport");
        if(passportName != null && !passportName.isEmpty()) {
            Passport passport = Passport.findPassportByNameForUser(getLoggedInUser().getId(), passportName);
            if(passport != null)
                return passport.childAge <= 3;
        }
        return true;
    }

    public static Boolean isSelectedPassportOver3() {
        String passportName = Http.Context.current().session().get("passport");
        if(passportName != null && !passportName.isEmpty()) {
            Passport passport = Passport.findPassportByNameForUser(getLoggedInUser().getId(), passportName);
            if(passport != null)
                return passport.childAge > 3;
        }
        return true;
    }


}