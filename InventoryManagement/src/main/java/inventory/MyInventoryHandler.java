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

/**
 * This class handles HTTP requests for the inventory management system.
 * It implements the HttpHandler interface which requires the handle method to be implemented.
 */
public class MyInventoryHandler implements HttpHandler {

    private MongoCollection<Document> inventoryCollection;

    /**
     * Constructor for MyInventoryHandler.
     *
     * @param invColl MongoDB collection to store inventory data
     */
    public MyInventoryHandler(MongoCollection<Document> invColl) {
        this.inventoryCollection = invColl;
    }


    /**
     * Handles HTTP requests.
     *
     * @param exchange The HttpExchange object encapsulating the HTTP request
     * @throws IOException If an input or output error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<String> uri = HTTPHelper.getPathArrayFromUri(exchange.getRequestURI());

        if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
            HandlePost(exchange, uri);
        else
            handleGet(exchange, uri);

    }

    /**
     * Handles HTTP GET requests.
     *
     * @param exchange The HttpExchange object encapsulating the HTTP request
     * @param uri      The request URI
     * @throws IOException If an input or output error occurs
     */
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


    /**
     * Handles HTTP POST requests.
     *
     * @param exchange The HttpExchange object encapsulating the HTTP request
     * @param uri      The request URI
     * @throws IOException If an input or output error occurs
     */
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


    /**
     * Creates a MongoDB Document from a JSON object representing an inventory.
     *
     * @param json The JSON object representing an inventory
     * @return The MongoDB Document representing the inventory
     */
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

    /**
     * Updates the inventory for a user in the MongoDB collection.
     *
     * @param userId      The user's ID
     * @param newDocument The new inventory document
     */
    private void updateInventory(int userId, Document newDocument) {
        String userIdNew = String.valueOf(userId);
        Document query = new Document("userId", userIdNew);
        UpdateOptions options = new UpdateOptions().upsert(true);
        this.inventoryCollection.updateOne(query, newDocument, options);
    }

    /**
     * Extracts a JSON object from an HTTP request.
     *
     * @param exchange The HttpExchange object encapsulating the HTTP request
     * @return The extracted JSON object
     * @throws IOException    If an input or output error occurs
     * @throws ParseException If an error occurs while parsing the JSON
     */
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

    /**
     * Retrieves the inventory for a user in JSON format.
     *
     * @param userId The user's ID
     * @return The user's inventory in JSON format
     */
    private String getInventoryForUserInJson(int userId) {
        String userIdNew = String.valueOf(userId);
        Document query = new Document().append("userId", userIdNew);
        FindIterable<Document> inventory = this.inventoryCollection.find(query).projection(new Document("_id", 0));
        return HTTPHelper.getJsonOutputFromIterableDocument(inventory);
    }
}
