package profile;
import java.io.IOException;
import java.io.OutputStream;

import org.bson.Document;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.Iterator;

public class MyFoodHandler implements HttpHandler {

    private MongoCollection<Document> foodCollection;

    public MyFoodHandler(MongoCollection<Document> foods) {
        this.foodCollection = foods;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set response headers
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);

        StringBuilder str = new StringBuilder();
        // string responseBody = "Hello! This is a Java HTTP server.";
        MongoCollection<Document> collection = this.foodCollection;
        FindIterable<Document> iterDoc = collection.find();
        // DBCursor cursor = collection.find(query)
        // return cursor.next().toString()
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            str.append(it.next());
            // System.out.println(it.next());
        }

        // Write the response body to the output stream
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(str.toString().getBytes());
        }
    }
}
