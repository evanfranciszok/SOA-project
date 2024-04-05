package profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.net.HttpURLConnection;
import org.bson.Document;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        List<String> uri = HTTPHelper.getPathArrayFromUri(exchange.getRequestURI());

        if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
            HandlePost(exchange, uri);
        else
            handleGet(exchange, uri);

    }

    private void handleGet(HttpExchange exchange, List<String> uri) throws IOException {
        String jsonOutput = "";
        if (!uri.get(0).equals("")) {
            int userId = Integer.parseInt(uri.get(0));
            jsonOutput = getUserInJson(userId);
        } else {
            FindIterable<Document> allFoodItems = this.preferenceCollection.find().projection(new Document("_id", 0));
            jsonOutput = HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonOutput.length());

        HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
    }

    private void HandlePost(HttpExchange exchange, List<String> uri) throws IOException {
        OutputStream os = exchange.getResponseBody();
        StringBuilder output = new StringBuilder();
        int httpOutputStatus = HttpURLConnection.HTTP_OK;
        try {
            JSONObject JsonBody = extractJsonFromHTTPRequest(exchange);
            output.append("Profile saved.\n");
            output.append(JsonBody.toString());
        } catch (ParseException e) {
            output.append("Incorrect user Profile provided.\n");
            output.append(e.toString());
            httpOutputStatus = HttpURLConnection.HTTP_BAD_REQUEST;
            e.printStackTrace();
        } finally {
            exchange.sendResponseHeaders(httpOutputStatus, output.length());
            os.write(output.toString().getBytes());
            exchange.close();
        }
    }

    private JSONObject extractJsonFromHTTPRequest(HttpExchange exchange) throws IOException, ParseException {
        int contentLength = Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-length"));
        StringBuilder buf = new StringBuilder(contentLength);
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        return (JSONObject) new JSONParser().parse(buf.toString());
    }

    private String getUserInJson(int userId) {
        Document filter = new Document("userId", userId);
        FindIterable<Document> allFoodItems = this.preferenceCollection.find(filter).projection(new Document("_id", 0));
        return HTTPHelper.getJsonOutputFromIterableDocument(allFoodItems);
    }
}
