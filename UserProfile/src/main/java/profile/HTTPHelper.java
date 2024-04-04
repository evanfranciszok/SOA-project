package profile;

import org.bson.Document;

import java.io.IOException;
import java.util.*;

import com.mongodb.client.FindIterable;

public class HTTPHelper {
    public static String getJsonOutputFromIterableDocument(FindIterable<Document> allFoodItems) {
        StringBuilder str = new StringBuilder();
        ArrayList<String> jsonArray = allFoodItems.map(Document::toJson).into(new ArrayList<>());
        str.append('[');
        for (String string : jsonArray) {
            str.append(string).append(',');
        }
        str.deleteCharAt(str.length() - 1);
        str.append(']');
        return str.toString();
    }

    public static void outputJson(java.io.OutputStream responseBody, String jsonOutput) throws IOException {
        responseBody.write(jsonOutput.getBytes());
        responseBody.flush();
        responseBody.close();
    }
}
