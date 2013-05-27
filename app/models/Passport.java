package models;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import enums.PassportTypeEnum;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;
import utils.DataUtil;

/**
 * User: Charles
 * Date: 5/4/13
 */

@MongoCollection(name = "passports")
public class Passport {

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

    public String passportName;

    public String userId;

    public PassportTypeEnum type;

    public BasicDBObject cardsComplete;

    public Passport() {   }

    public Passport(String passportName, String userId, PassportTypeEnum type) {
        this.passportName = passportName;
        this.userId = userId;
        this.type = type;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PassportTypeEnum getType() {
        return type;
    }

    public void setType(PassportTypeEnum type) {
        this.type = type;
    }

    public static Boolean isPassportForUser(String userId) {
        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("userId", userId));

            return (cursorDoc.hasNext());
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }


}