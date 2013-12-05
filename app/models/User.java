package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBList;
import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;
import utils.DataUtil;
import utils.EncryptionUtil;

import java.util.List;

@MongoCollection(name = "users")
public class User {

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

    public String username;

    public String password;

    public String securityQuestionOne;

    public String securityAnswerOne;

    public String securityQuestionTwo;

    public String securityAnswerTwo;

    public String securityQuestionThree;

    public String securityAnswerThree;

    public User() {   }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Boolean isUsernameTaken(String username) {
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("username", username));

            return (cursorDoc.hasNext());
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User findUserByName(String username) {
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("username", username));

            if (cursorDoc.hasNext())
                return ((User) cursorDoc.next());
            else
                return null;
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<User> findAll() {
        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

            DBCursor cursorDoc = collection.find();

            return ((List<User>)cursorDoc.toArray());
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDecryptedPasswordForUser(String username) {
        JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

        DBCursor cursorDoc = collection.find(DBQuery.is("username", username));

        User user = (User) cursorDoc.next();
        EncryptionUtil decryptor;

        try {
            decryptor = new EncryptionUtil();
        } catch (Exception e) {
            return null;
        }

        if (user != null)
            return decryptor.decrypt(user.password);
        else
            return null;
    }

}