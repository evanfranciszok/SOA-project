package inventory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import com.mongodb.client.FindIterable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HTTPHelperTest {

    @Test
    void testGetJsonOutputFromIterableDocumentEmpty() {
        // Mock the iterable and the map operation to return an empty JSON array
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoIterable<Object> mockMongoIterable = mock(MongoIterable.class);
        MongoCursor<Object> mockCursor = mock(MongoCursor.class);
        when(mockIterable.map(any())).thenReturn(mockMongoIterable);
        when(mockMongoIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false); // No more elements in the cursor
        String result = HTTPHelper.getJsonOutputFromIterableDocument("123", mockIterable);
        assertEquals("{}", result, "Expected empty JSON object when no documents are present");
    }

    @Test
    void testOutputJson() throws Exception {
        java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
        String jsonOutput = "{\"key\":\"value\"}";
        HTTPHelper.outputJson(os, jsonOutput);
        assertEquals(jsonOutput, os.toString(), "The output JSON should match the input JSON");
    }

    @Test
    void testGetPathArrayFromUri() {
        URI testUri = URI.create("http://example.com/one/two/three");
        List<String> pathSegments = HTTPHelper.getPathArrayFromUri(testUri);
        assertArrayEquals(new String[]{"one", "two", "three"}, pathSegments.toArray(), "Path segments should be correctly extracted from URI");
    }
}
