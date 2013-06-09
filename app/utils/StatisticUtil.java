package utils;

import controllers.MasterController;
import models.Survey;
import models.User;

/**
 * User: Charles
 * Date: 6/8/13
 */
public class StatisticUtil {


    public static Integer getTotalUsers() {
        return User.findAll().size();
    }

    public static Double getAverageGuardianAge() {
        Double totalAges = 0.0;
        Double totalSurveys = 0.0;
        for(Survey survey : Survey.findAll()) {
            if(survey.age != null && survey.age > 0 && survey.age < 100)  {
                totalAges += survey.age;
                totalSurveys++;
            }
        }
        return (totalAges/totalSurveys);
    }
}
