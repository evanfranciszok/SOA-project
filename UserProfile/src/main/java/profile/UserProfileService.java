package profile;

import com.sun.net.httpserver.HttpServer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.io.IOException;

import java.net.InetSocketAddress;

public class UserProfileService {

    public static void main(String[] args) throws IOException {
        String uri = "mongodb://profile_database:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        // String uri =
        // "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        MongoDatabase database;
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            MongoIterable<String> list = mongoClient.listDatabaseNames();
            for (String name : list) {
                System.out.println(name);
            }
            database = mongoClient.getDatabase("profiles");
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/food", new MyFoodHandler(database.getCollection("foods")));
            server.createContext("/diet", new MyDietHandler(database.getCollection("diets")));
            server.createContext("/profile", new MyProfileHandler(database.getCollection("preferences")));
            server.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

        }
    }
}