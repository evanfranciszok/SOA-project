import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class UserProfileService {

    public static void main(String[] args) throws IOException {
        // Create a new HTTP server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a context for the root path "/"
        server.createContext("/", new MyHandler());
        server.createContext("/profiles/", new GetUserProfileHandler());

        // Start the server
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Set response headers
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);

            // Prepare the response body
            String responseBody = "Hello! This is a Java HTTP server.";

            // Write the response body to the output stream
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBody.getBytes());
            }
        }
    }

    static class GetUserProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get the userId from the request URI
            String requestURI = exchange.getRequestURI().toString();
            String[] pathSegments = requestURI.split("/");
            if (pathSegments.length < 3 || pathSegments[2].isEmpty()) {
                sendResponse(exchange, "User ID not provided", 400); // Bad Request
                return;
            }
            String userId = pathSegments[pathSegments.length - 1];

            // Dummy implementation: Retrieve user profile based on userId
            String userProfile = getUserProfile(userId);
            if ( userId != null) {
                sendResponse(exchange, userProfile, 200);
            } else {
                sendResponse(exchange, "User profile not found", 404);
            }
        }

        // Dummy method to retrieve user profile (replace with actual logic)
        private String getUserProfile(String userId) {
            // Placeholder implementation: Return a hardcoded user profile for demonstration
            return "{" +
            "\"userId\": \"" + userId + "\"," +
            "\"allergies\": [\"peanuts\", \"shellfish\"]," +
            "\"isVegetarian\": true," +
            "\"dislikedFoods\": [\"mushrooms\", \"olives\"]" +
        "}";
        }
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}