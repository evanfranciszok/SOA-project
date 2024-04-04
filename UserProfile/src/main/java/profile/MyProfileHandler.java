package profile;

import java.io.IOException;
import java.io.OutputStream;

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
        FindIterable<Document> allFoodItems = this.preferenceCollection.find().projection(new Document("_id", 0));
        String jsonOutput = HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonOutput.length());
        
        HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
    }
}
