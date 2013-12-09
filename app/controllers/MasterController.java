package controllers;

import models.Passport;
import models.User;
import org.mongojack.WriteResult;
import play.mvc.Controller;
import play.mvc.Http;
import utils.CodeGenerator;
import utils.DataUtil;
import org.mongojack.JacksonDBCollection;
import utils.EncryptionUtil;
import views.html.index;

/**
 * User: Charles
 * Date: 5/1/13
 */

public abstract class MasterController extends Controller {

    private static DataUtil dataUtil;

    public static User getLoggedInUser() {
        String userID = Http.Context.current().session().get("user");
        if(userID == null) {
            User newUser;

            if (User.isUsernameTaken("guest")) {
                return User.findUserByName("guest");
            }

            else {
                try {
                    JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);
                    EncryptionUtil encryptionUtil = new EncryptionUtil();
                    newUser = new User("guest", encryptionUtil.encrypt("public"));

                    WriteResult<User, String> result = collection.insert(newUser);

                    Http.Context.current().session().put("user", result.getSavedId());

                } catch (Exception e) {
                    flash("error", "Unexpected error: " + e.toString());
                }
            }
            return User.findUserByName("guest");
        }

        return  (User)DataUtil.getEntityById("users", User.class, userID);

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
        if(dataUtil == null)
            dataUtil = new DataUtil();

        return dataUtil.getCollection(collection, clazz);
    }


}
