package controllers;

import models.Passport;
import models.Survey;
import models.User;
import play.mvc.Controller;
import play.mvc.Http;
import utils.CodeGenerator;
import utils.DataUtil;
import org.mongojack.JacksonDBCollection;

/**
 * User: Charles
 * Date: 5/1/13
 */

public abstract class MasterController extends Controller {

    public static User getLoggedInUser() {
        String userID = Http.Context.current().session().get("user");
        if(userID == null) {
            return null;
        }
        else {
           return  (User)DataUtil.getEntityById("users", User.class, userID);
        }
    }

    public static String getCodeOfTheDay() {
        return CodeGenerator.getCodeOfTheDay();
    }

    public static Boolean userHasPassport() {
        return Passport.isPassportForUser(getLoggedInUser().getId());
    }

    public static Boolean isAuthorized() {
        return (getLoggedInUser().username.equals("IowaAdministrator"));
    }

    public static JacksonDBCollection getCollection(String collection, Class clazz) {
        if(collectionService == null)
            collectionService = new CollectionService();

        return collectionService.getCollection(collection, clazz);
    }

}
