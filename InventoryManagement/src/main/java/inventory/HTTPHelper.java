package inventory;

import org.bson.Document;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import com.mongodb.client.FindIterable;

public class HTTPHelper {
    public static String getJsonOutputFromIterableDocument(String userId, FindIterable<Document> document) {
        List<String> jsonArray = document.map(Document::toJson).into(new ArrayList<>());
        return jsonArray.isEmpty() ? "{}" : jsonArray.get(0);
    }

    public static void outputJson(java.io.OutputStream responseBody, String jsonOutput) throws IOException {
        responseBody.write(jsonOutput.getBytes());
        responseBody.flush();
        responseBody.close();
    }

    public static List<String> getPathArrayFromUri(URI uri) {
        return Arrays.asList(uri.getPath().substring(1).split("/"));
    }
}
