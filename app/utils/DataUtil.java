package utils;

import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.MongoClient;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import models.User;

import java.net.UnknownHostException;

/**
 * User: Charles
 * Date: 4/28/13
 */
public class DataUtil {

    public static DB getDB() {

        try {
            MongoClient mongoClient = new MongoClient("ds061787.mongolab.com", 61787);
            

//          mongoClient.setReadPreference(ReadPreference.primary());
//
            DB dataBase = mongoClient.getDB("heroku_app15452455");

            /*TEST DB*/
//            MongoClient mongoClient = new MongoClient( );
//            DB dataBase = mongoClient.getDB("icm");

            //todo this needs to go in an ignored config file
            dataBase.authenticate("heroku_app15452455", "73mi73eoolvr4s7v47ugutfru9".toCharArray());

            return dataBase;

        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static JacksonDBCollection getCollection(String collection, Class clazz) {

        try {
            return JacksonDBCollection.wrap(getDB().getCollection(collection), clazz);

        } catch (Exception e) {
            return null;
        }
    }

    public static Object getEntityById(String collection, Class clazz, String id) {

        try {

            JacksonDBCollection jacksonDBCollection =  JacksonDBCollection.wrap(getDB().getCollection(collection), clazz, String.class);
            return jacksonDBCollection.findOneById(id);

        } catch (Exception e) {
            return null;
        }

    }

    public static Boolean isDatabase() {

        try {
            JacksonDBCollection<User, String> collection = DataUtil.getCollection("users", User.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("username", "test"));

            if (cursorDoc.hasNext())
                return true;
            else
                return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

}
