package controllers;

import com.mongodb.MongoException;
import enums.PassportTypeEnum;
import models.Passport;
import models.User;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.mvc.*;
import play.data.*;
import utils.DataUtil;
import utils.EncryptionUtil;
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
                //todo check name is unique
        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            Passport newPassport = new Passport(passportName,MasterController.getLoggedInUser().getId(),passportType);

            WriteResult<Passport, String> result = collection.insert(newPassport);

        } catch (Exception e) {
            flash("error", "Unexpected error: " + e.toString());
            return internalServerError(landing.render(getLoggedInUser(), passportForm));
        }

        return ok(landing.render(getLoggedInUser(), passportForm));

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

}