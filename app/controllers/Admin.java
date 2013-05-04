package controllers;

import enums.MessageLevel;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.*;
import play.mvc.*;
import play.data.*;

import utils.DataUtil;
import utils.EncryptionUtil;
import utils.StringUtil;
import views.html.*;

import models.*;

import static play.data.Form.form;

public class Admin extends MasterController {

    public static Result index() {
        return ok(admin.render(DataUtil.dump("users",User.class)));
    }

}
