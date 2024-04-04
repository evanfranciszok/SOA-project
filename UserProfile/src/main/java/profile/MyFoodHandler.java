package profile;

import java.io.IOException;
import org.bson.Document;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MyFoodHandler implements HttpHandler {

    private MongoCollection<Document> foodCollection;

    public MyFoodHandler(MongoCollection<Document> foods) {
        this.foodCollection = foods;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FindIterable<Document> allFoodItems = this.foodCollection.find().projection(new Document("_id", 0));
        String jsonOutput = HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonOutput.length());
        
        HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
    }
}
