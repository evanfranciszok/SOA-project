package profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONArray;
// import org.bson.json.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

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
            updateProfileInDatabase(createDocumentFromJson(JsonBody));
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

    public Document createDocumentFromJson(JSONObject json) {
        return new Document().append("firstName", json.get("firstName").toString())
                .append("lastName", json.get("lastName").toString())
                .append("portionSize", json.get("portionSize").toString())
                .append("Allergies", toStringArray((JSONArray) json.get("Allergies")))
                .append("Diet", toStringArray((JSONArray) json.get("Diet")));
    }

    public static List<String> toStringArray(JSONArray array) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            list.add((String) array.get(i));
        }
        return list;
    }

    private void updateProfileInDatabase(Document jsonBody) {
        // Document query = new Document().append("title", "Cool Runnings 2");
        // Bson updates = Updates.combine(
        // Updates.set("runtime", 99),
        // Updates.addToSet("genres", "Sports"),
        // Updates.currentTimestamp("lastUpdated"),
        // Updates.set);
        // UpdateOptions options = new UpdateOptions().upsert(true);
        // try {
        // UpdateResult result = this.preferenceCollection.updateOne(query, updates,
        // options);
        // System.out.println("Modified document count: " + result.getModifiedCount());
        // System.out.println("Upserted id: " + result.getUpsertedId()); // only
        // contains a value when an upsert is
        // // performed
        // } catch (MongoException me) {
        // System.err.println("Unable to update due to an error: " + me);
        // }
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
