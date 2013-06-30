package models;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;
import utils.DataUtil;

import java.util.*;

/**
 * User: Charles
 * Date: 5/10/13
 */

@MongoCollection(name = "surveys")
public class Survey {

    private String id;

    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public String userId;

    public Boolean isStageOneComplete;
    public Integer playImportance;
    public Integer playingRelatedToLearningKnowledge;
    public Integer motivatedToLearnAboutPlay;
    public String hoursPerDayPlayingWithChildren;

    public Boolean isStageTwoComplete;
    public Boolean isParent;
    public String relationshipToChild;
    public Integer age;
    public Integer zip;
    public Integer childrenInCare;
    public BasicDBObject childAges;
    public String race;
    public String ethnicity;
    public String income;
    public Boolean isTwoParentHousehold;
    public Boolean isEnglishPrimaryLanguage;
    public String primaryLanguage;

    public Boolean isStageThreeComplete;
    public BasicDBObject agesComplete;
    public BasicDBObject hoursPerDayPlaying;
    public BasicDBObject topThreePlayTimeActivities;
    public BasicDBObject whoPlayWithRanking;
    public BasicDBObject howOftenReadToChild;
    public BasicDBObject howOftenPlayGames;

    public Boolean isStageFourComplete;
    public String frequencyTakeChildrenToICM;
    public String childsFavoriteExhibit;
    public String yourFavoriteExhibit;

    public Boolean isStageFiveComplete;
    public Integer howMuchHaveYouLearned;
    public String howOftenDoYouPlay;
    public Integer howMuchFunDoYouHave;
    public Boolean isRecommend;

    public Survey() {   }

    public Survey(String userId) {
        this.userId = userId;
        this.hoursPerDayPlaying = new BasicDBObject();
        this.topThreePlayTimeActivities = new BasicDBObject();
        this.whoPlayWithRanking = new BasicDBObject();
        this.howOftenReadToChild = new BasicDBObject();
        this.howOftenPlayGames = new BasicDBObject();
        this.childAges = new BasicDBObject();
    }

    public static Survey findByUser(User user) {
        JacksonDBCollection<Survey, String> collection = DataUtil.getCollection("surveys", Survey.class);

        DBCursor cursorDoc = collection.find(DBQuery.is("userId", user.getId()));

        if(cursorDoc.hasNext())
            return ((Survey)cursorDoc.next());
        else
            return null;
    }

    public Boolean getIsStageOneComplete() {
        return isStageOneComplete;
    }

    public void setIsStageOneComplete(Boolean is) {
        isStageOneComplete = is;
    }

    public Boolean getIsStageTwoComplete() {
        return isStageTwoComplete;
    }

    public void setIsStageTwoComplete(Boolean is) {
        isStageTwoComplete = is;
    }

    public Boolean getIsStageThreeComplete() {
        return isStageThreeComplete;
    }

    public void setIsStageThreeComplete(Boolean is) {
        isStageThreeComplete = is;
    }

    public Boolean getIsStageFourComplete() {
        return isStageFourComplete;
    }

    public void setIsStageFourComplete(Boolean is) {
        isStageFourComplete = is;
    }

    public Boolean getIsStageFiveComplete() {
        return isStageFiveComplete;
    }

    public void setIsStageFiveComplete(Boolean is) {
        isStageFiveComplete = is;
    }

    public BasicDBObject getChildAges() {
        return childAges;
    }

    public BasicDBObject getAgesComplete() {
        return agesComplete;
    }

    public List<Integer> getChildAgesList() {

        List<Integer> childAgeList = new ArrayList<Integer>();

        if(getChildAges() == null)
            return Collections.EMPTY_LIST;

        for (String key : getChildAges().keySet()) {
            if (key != null && !key.isEmpty()) {
                String value = (String)getChildAges().get(key);
                if(value != null && !value.isEmpty()) {
                    Integer age = Integer.parseInt(value);
                    childAgeList.add(age);
                }
            }
        }

        return childAgeList;
    }

    public Integer getNextSurveyAge() {
        Boolean isAgeComplete = false;

        for(Integer age : this.getChildAgesList()) {
            for (String key : getAgesComplete().keySet()) {
                if (key != null && !key.isEmpty()) {
                    Integer ageComplete = Integer.parseInt(key);
                    if(ageComplete.equals(age))
                        isAgeComplete = true;
                }
            }
            if(!isAgeComplete)
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
