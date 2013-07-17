package utils;

import models.Survey;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

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
        for(Survey survey : SurveyUtil.findAll()) {
            if(survey.age != null && survey.age > 0 && survey.age < 100)  {
                totalAges += survey.age;
                totalSurveys++;
            }
        }
        return (totalAges/totalSurveys);
    }

    public static Double getAverageChildrenInCare() {
        Double totalChildren = 0.0;
        Double totalSurveys = 0.0;
        for(Survey survey : SurveyUtil.findAll()) {
            if(survey.childrenInCare != null && survey.childrenInCare > 0)  {
                totalChildren += survey.childrenInCare;
                totalSurveys++;
            }
        }
        return (totalChildren/totalSurveys);
    }

    public static String getAllSurveyData() {
        StringBuilder rawData = new StringBuilder();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            for (Survey survey : SurveyUtil.findAll()) {
                String json = ow.writeValueAsString(survey);
                rawData.append("------------------------------------Survey " + survey.getId() + "--------------------------------------------");
                rawData.append(json);
            }
        } catch (Exception e) {
            //
        }
        return rawData.toString();
    }
}
