package profile;

import org.bson.Document;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import com.mongodb.client.FindIterable;

public class HTTPHelper {
    public static String getJsonOutputFromIterableDocument(FindIterable<Document> allFoodItems) {
        StringBuilder str = new StringBuilder();
        ArrayList<String> jsonArray = allFoodItems.map(Document::toJson).into(new ArrayList<>());
        if (jsonArray.size() > 1)
            str.append('[');
        for (String string : jsonArray)
            str.append(string).append(',');
        if (str.length() > 1)
            str.deleteCharAt(str.length() - 1);
        if (jsonArray.size() > 1)
            str.append(']');
        return str.toString();
    }

    public static void outputJson(java.io.OutputStream responseBody, String jsonOutput) throws IOException {
        responseBody.write(jsonOutput.getBytes());
        responseBody.flush();
        responseBody.close();
    }

    public static List<String> getPathArrayFromUri(URI uri) {
        return Arrays.asList(uri.getPath().replace("/profile/", "").replace("/profile", "").split("/"));
    }
}
