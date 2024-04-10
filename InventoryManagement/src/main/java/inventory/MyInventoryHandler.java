package inventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.HttpURLConnection;
import org.bson.Document;
import org.json.simple.JSONArray;
// import org.bson.json.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;

public class MyInventoryHandler implements HttpHandler {

    private MongoCollection<Document> inventoryCollection;

    public MyInventoryHandler(MongoCollection<Document> invColl) {
        this.inventoryCollection = invColl;
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
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        if (!uri.get(0).equals("")) {
            int userId = Integer.parseInt(uri.get(0));
            jsonOutput = getInventoryForUserInJson(userId);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonOutput.length());
        } else {
            jsonOutput = "No userId given to retrieve Inventory, should be in the form of Xinventory_urlX/{userId}";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, jsonOutput.length());
        }
        HTTPHelper.outputJson(exchange.getResponseBody(), jsonOutput);
    }

    private void HandlePost(HttpExchange exchange, List<String> uri) throws IOException {
        int httpOutputStatus = HttpURLConnection.HTTP_OK;
        StringBuilder output = new StringBuilder();
        OutputStream os = exchange.getResponseBody();
        try {
            int userId = Integer.parseInt(uri.get(0));
            JSONObject JsonBody = extractJsonFromHTTPRequest(exchange);
            // if (uri.size() > 1) {
            // String foodName = uri.get(0);
            // updateFoodItemInInventory(userId, foodName,
            // createDocumentFromJsonForInventoryItem(JsonBody));
            // } else {
            updateInventory(userId, createDocumentFromJsonForInventory(JsonBody));
            // }
            output.append("Profile saved.\n");
            output.append(JsonBody.toString());
        } catch (NumberFormatException e) {
            output.append("Incorrect UserId given. Must be numeric, ");
            output.append(e.getMessage());
            httpOutputStatus = HttpURLConnection.HTTP_BAD_REQUEST;
            e.printStackTrace();
        } catch (ParseException e) {
            output.append("could not parse provided Json, ");
            output.append(e.getMessage());
            httpOutputStatus = HttpURLConnection.HTTP_BAD_REQUEST;
            e.printStackTrace();
        }
        exchange.sendResponseHeaders(httpOutputStatus, output.length());
        os.write(output.toString().getBytes());
        exchange.close();
    }

    // public Document createDocumentFromJsonForInventoryItem(JSONObject json) {

    //     return new Document("$set", new Document("name", json.get("name"))
    //             .append("quantity", json.get("quantity"))
    //             .append("expiry_date", json.get("expiry_date")));
    // }

    public Document createDocumentFromJsonForInventory(JSONObject json) {
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = ((JSONArray) json.get("food_inventory")).iterator();
        List<Document> foodItems = new ArrayList<Document>();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            foodItems.add(new Document("name", obj.get("name"))
                    .append("quantity", obj.get("quantity"))
                    .append("expiry_date", obj.get("expiry_date")));
        }

        return new Document("$set", new Document("userId", "1")
                .append("food_inventory", foodItems));
    }

    // public static List<String> toStringArray(JSONArray array) {
    //     List<String> list = new ArrayList<String>();
    //     for (int i = 0; i < array.size(); i++) {
    //         list.add((String) array.get(i));
    //     }
    //     return list;
    // }

    // private void updateFoodItemInInventory(int userId, String foodName, Document
    // newDocument) {
    // String userIdNew = String.valueOf(userId);
    // Document query = new Document("userId", userIdNew)
    // .append("food_inventory.name", foodName);
    // UpdateOptions options = new UpdateOptions().upsert(true);
    // this.inventoryCollection.updateOne(query, newDocument, options);
    // }

    private void updateInventory(int userId, Document newDocument) {
        String userIdNew = String.valueOf(userId);
        Document query = new Document("userId", userIdNew);
        UpdateOptions options = new UpdateOptions().upsert(true);
        this.inventoryCollection.updateOne(query, newDocument, options);
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

    private String getInventoryForUserInJson(int userId) {
        String userIdNew = String.valueOf(userId);
        Document query = new Document().append("userId", userIdNew);
        FindIterable<Document> inventory = this.inventoryCollection.find(query).projection(new Document("_id", 0));
        return HTTPHelper.getJsonOutputFromIterableDocument(inventory);
    }
}
