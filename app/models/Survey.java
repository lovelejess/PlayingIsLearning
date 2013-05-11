package models;

import com.mongodb.BasicDBObject;
import net.vz.mongodb.jackson.MongoCollection;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

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

    public Boolean isParent;
    public String relationshipToChild;
    public String age;
    public String zip;
    public String childrenInCare;
    public BasicDBObject childAges;
    public String race;
    public String ethnicity;
    public String income;
    public Boolean isTwoParentHousehold;
    public String isEnglishPrimaryLanguage;
    public String primaryLanguage;


    public Integer playImportance;
    public Integer playingRelatedToLearningKnowledge;
    public Integer motivatedToLearnAboutPlay;
    public String hoursPerDayPlayingWithChildren;


    public String frequencyTakeChildrenToICM;
    public String childsFavoriteExhibit;
    public String yourFavoriteExhibit;


    public Integer hoursPerDayPlaying;
    public BasicDBObject topThreePlayTimeActivities;
    public BasicDBObject whoPlayWithRanking;


    public Integer howMuchHaveYouLearned;
    public String howOftenDoYouPlay;
    public Integer howMuchFunDoYouHave;
    public Boolean isRecommend;

//    public String howOftenReadToChild;
//    public String howOftenPlayGames;


    public Survey() {   }


}
