package inventory;

import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class InventoryService {

    private static final String DATABASE_NAME = "Inventory";
    private static final String COLLECTION_NAME = "inventory";

    public static void main(String[] args) throws IOException {
        String uri =
        "mongodb://profile_database:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        // String uri = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        MongoDatabase database;
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            checkOrCreateDBIfNotExists(mongoClient);
            database = mongoClient.getDatabase("Inventory");
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", new MyInventoryHandler(database.getCollection(COLLECTION_NAME)));
            server.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

        }
    }

    private static void checkOrCreateDBIfNotExists(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

        if (!database.listCollectionNames().into(new ArrayList<>()).contains(COLLECTION_NAME)) {
            database.createCollection(COLLECTION_NAME);
            insertDataInTheCollection(database.getCollection(COLLECTION_NAME));
        }
        System.out.println("database connected and the correctly configured");
    }

    private static void insertDataInTheCollection(MongoCollection<Document> mongoCollection) {
        try {
            String jsonData = new String(Files
                    .readAllBytes(Paths.get("src/main/resources/" + DATABASE_NAME + "." + COLLECTION_NAME + ".json")));

            Gson gson = new Gson();
            List<Map<String, Object>> jsonObjects = gson.fromJson(jsonData, ArrayList.class);

            List<Document> documents = new ArrayList<>();
            for (Map<String, Object> jsonObject : jsonObjects) {
                jsonObject.remove("_id");
                documents.add(new Document(jsonObject));
            }
            mongoCollection.insertMany(documents);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}