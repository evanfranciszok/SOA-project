package profile;

import java.io.IOException;
import java.io.OutputStream;

import org.bson.Document;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.Iterator;

public class MyProfileHandler implements HttpHandler {

    private MongoCollection<Document> preferenceCollection;

    public MyProfileHandler(MongoCollection<Document> preferences) {
        this.preferenceCollection = preferences;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set response headers
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);

        StringBuilder str = new StringBuilder();

        MongoCollection<Document> collection = preferenceCollection;
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            str.append(it.next());
            // System.out.println(it.next());
        }

        // Prepare the response body

        // Write the response body to the output stream
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(str.toString().getBytes());
        }
    }
}
