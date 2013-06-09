package controllers;

import play.mvc.*;
import utils.StatisticUtil;
import views.html.*;

public class Admin extends MasterController {

    public static Result index() {
        return ok(admin.render());
    }

}
