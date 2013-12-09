package utils;

import com.mongodb.MongoException;
import models.Survey;
import com.mongodb.BasicDBList;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Charles
 * Date: 6/30/13
 */
public class SurveyUtil {

    public static BasicDBList getAgesComplete(Survey survey) {

        if(survey.getAgesComplete() == null)
            return new BasicDBList();

        return survey.getAgesComplete();
    }

    public static List<Integer> getChildAgesList(Survey survey) {

        List<Integer> childAgeList = new ArrayList<Integer>();

        if(survey.getChildAges() == null || survey.getChildAges().isEmpty())
            return new ArrayList<Integer>();

        for (String key : survey.getChildAges().keySet()) {
            if (key != null && !key.isEmpty()) {
                String value = (String)survey.getChildAges().get(key);
                if(value != null && !value.isEmpty() && !value.equals("")) {
                    Integer age = Integer.parseInt(value);
                    childAgeList.add(age);
                }
            }
        }

        return childAgeList;
    }

    public static Integer getNextSurveyAge(Survey survey) {
        for(Integer age : getChildAgesList(survey)) {
            if(!getAgesComplete(survey).keySet().contains(age.toString()))
                return age;
        }

        return null;
    }

    public static List<Survey> findAll() {
        try {
            JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);

            DBCursor cursorDoc = collection.find();

            return ((List<Survey>)cursorDoc.toArray());
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

}
