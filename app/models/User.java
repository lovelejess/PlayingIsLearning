package models;

import javax.validation.*;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.data.validation.Constraints.*;
import utils.DataUtil;
import utils.EncryptionUtil;

public class User {

    private String id;

    @MinLength(value = 4)
    public String username;

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

        return (cursorDoc.hasNext());
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