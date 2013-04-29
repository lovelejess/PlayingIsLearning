package models;

import javax.validation.*;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.codehaus.jackson.annotate.JsonProperty;
import org.mongojack.ObjectId;
import play.data.validation.Constraints.*;
import utils.DataUtil;


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

    @MinLength(value = 4)
    public String username;

    @MinLength(value = 2)
    public String realname;

    @MinLength(value = 3)
    public String email;

    @MinLength(value = 6)
    public String password;

    @Valid
    public Profile profile;
    
    public User() {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Boolean isUsernameTaken(String username) {
        JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

        DBCursor cursorDoc = collection.find(DBQuery.is("username", username));

        return  (cursorDoc.hasNext());
    }
    
    public static class Profile {

        public String country;
        public String address;
        public Integer age;
        
        public Profile() {}
        
        public Profile(String country, String address, Integer age) {
            this.country = country;
            this.address = address;
            this.age = age;
        }
    }
    
}