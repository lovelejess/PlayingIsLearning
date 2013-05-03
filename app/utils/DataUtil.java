package utils;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import net.vz.mongodb.jackson.JacksonDBCollection;

import java.net.UnknownHostException;

/**
 * User: Charles
 * Date: 4/28/13
 */
public class DataUtil {

    public static DB getDB() {

        MongoClient mongoClient;

        try {

            mongoClient = new MongoClient( "ds061787.mongolab.com" , 61787 );

            return mongoClient.getDB("heroku_app15452455");

        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static JacksonDBCollection getCollection(String collection, Class clazz) {

        try {

            return JacksonDBCollection.wrap(getDB().getCollection(collection), clazz, String.class);

        } catch (Exception e) {
            return null;
        }

    }

    //            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);
//
//            User newUser = new User("testuSer", "testPaSs");
//
//            WriteResult<User, String> result = collection.insert(newUser);
//
//            DBCursor cursorDoc = collection.find();
//
//            while (cursorDoc.hasNext()) {
//                User us = (User)cursorDoc.next();
//                System.out.println(us.username);
//            }


}
