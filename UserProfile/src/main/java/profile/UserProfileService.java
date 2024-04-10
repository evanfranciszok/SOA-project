package profile;

import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class UserProfileService {

    private static final String DATABASE_NAME = "profiles";
    private static final String FOOD_COLLECTION_NAME = "foods";
    private static final String DIET_COLLECTION_NAME = "diets";
    private static final String PROFILE_COLLECTION_NAME = "preferences";

    public static void main(String[] args) throws IOException {
        // String uri =
        // "mongodb://profile_database:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        String uri = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        MongoDatabase database;
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            checkOrCreateDBIfNotExists(mongoClient);
            database = mongoClient.getDatabase(DATABASE_NAME);
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/food", new MyFoodHandler(database.getCollection(FOOD_COLLECTION_NAME)));
            server.createContext("/diet", new MyDietHandler(database.getCollection(DIET_COLLECTION_NAME)));
            server.createContext("/profile", new MyProfileHandler(database.getCollection(PROFILE_COLLECTION_NAME)));
            server.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

        }
    }

    private static void checkOrCreateDBIfNotExists(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        List<String> collectionNames = Arrays.asList(FOOD_COLLECTION_NAME, DIET_COLLECTION_NAME,
                PROFILE_COLLECTION_NAME);
        // Check if the database exists, create it if it doesn't
        for (String collectionName : collectionNames) {
            if (!database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
                database.createCollection(collectionName);
                insertDataInTheCollection(database.getCollection(collectionName), collectionName);
            }
        }
        System.out.println("database connected and the correctly configured");
    }

    private static void insertDataInTheCollection(MongoCollection<Document> mongoCollection, String collectionName) {
        try {
            String jsonData = new String(Files
                    .readAllBytes(Paths.get("src/main/resources/" + DATABASE_NAME + "." + collectionName + ".json")));

            Gson gson = new Gson();
            List<Map<String, Object>> jsonObjects = gson.fromJson(jsonData, ArrayList.class);

            List<Document> documents = new ArrayList<>();
            for (Map<String, Object> jsonObject : jsonObjects) {
                jsonObject.remove("_id");
                documents.add(new Document(jsonObject));
            }
            mongoCollection.insertMany(documents);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}