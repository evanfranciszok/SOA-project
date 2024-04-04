package profile;

import org.bson.Document;
import java.util.*;

import com.mongodb.client.FindIterable;

public class HTTPHelper {
    static String getJsonOutputFromIterableDocument(FindIterable<Document> allFoodItems) {
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
}
