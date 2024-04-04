package profile;

import com.sun.net.httpserver.HttpServer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;

import org.bson.Document;

import java.net.InetSocketAddress;
import java.util.Iterator;

public class UserProfileService {

    public static void main(String[] args) throws IOException {
        String uri = "mongodb://profile_database:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        // String uri = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        MongoClient mongoClient;
        try {
            mongoClient = MongoClients.create(uri);
        } finally {

        }
        MongoIterable<String> list = mongoClient.listDatabaseNames();
        for (String name : list) {
            System.out.println(name);
        }
        // MongoDatabase database = mongoClient.getDatabase("todoapp");
        // MongoCollection<Document> collection = database.getCollection("todos");
        // FindIterable<Document> iterDoc = collection.find();
        // Iterator it = iterDoc.iterator();
        // while (it.hasNext()) {
        //     System.out.println(it.next());
        // }

        // Create a new HTTP server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a context for the root path "/"
        server.createContext("/", new MyHandler());
        // server.createContext("/profiles/", new GetUserProfileHandler());

        // Start the server
        server.start();
        System.out.println("Server started on port 8080");
    }
}