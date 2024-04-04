package profile;

import java.io.IOException;
import java.util.List;

import org.bson.Document;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MyProfileHandler implements HttpHandler {

    private MongoCollection<Document> preferenceCollection;

    public MyProfileHandler(MongoCollection<Document> preferences) {
        this.preferenceCollection = preferences;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<String> uri = HTTPHelper.getHello(exchange.getRequestURI());
        String jsonOutput = "";

        if(!uri.get(0).equals("")) {
            int userId = Integer.parseInt(uri.get(0));
            jsonOutput = getUserJson(userId);
        } else {
            FindIterable<Document> allFoodItems = this.preferenceCollection.find().projection(new Document("_id", 0));
            jsonOutput = HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
        }
        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonOutput.length());
        
        HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
    }

    private String getUserJson(int userId) {
        Document filter = new Document("userId", userId);
        FindIterable<Document> allFoodItems = this.preferenceCollection.find(filter).projection(new Document("_id", 0));
        return HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
    }
}
