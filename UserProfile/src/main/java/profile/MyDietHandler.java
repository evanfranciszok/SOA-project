package profile;

import org.bson.Document;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MyDietHandler implements HttpHandler {

    private MongoCollection<Document> dietCollection;

    public MyDietHandler(MongoCollection<Document> diets) {
        this.dietCollection = diets;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("handle diet request");
        FindIterable<Document> allFoodItems = this.dietCollection.find().projection(new Document("_id", 0));
        String jsonOutput = HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
        
        try {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonOutput.length());

            HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
