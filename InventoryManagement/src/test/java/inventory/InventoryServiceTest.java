package inventory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpServer;

class InventoryServiceTest {

    private MongoClient mongoClientMock;
    private MongoDatabase mongoDatabaseMock;


    @BeforeEach
    void setUp() {
        mongoClientMock = mock(MongoClient.class);
        mongoDatabaseMock = mock(MongoDatabase.class);
        MongoCollection mockCollection = mock(MongoCollection.class);
        when(mongoClientMock.getDatabase(anyString())).thenReturn(mongoDatabaseMock);
        when(mongoDatabaseMock.getCollection(anyString())).thenReturn(mockCollection);
    }

    @Test
    void testMainSuccess() throws IOException {
        // Mock HttpServer
        HttpServer serverMock = mock(HttpServer.class);
        doNothing().when(serverMock).start();
    }


    @Test
    void testCheckOrCreateDBIfNotExists() {
        // Mock interactions and verify the results in MongoDB operations
        MongoIterable<String> mockIterable = mock(MongoIterable.class);
        when(mongoDatabaseMock.listCollectionNames()).thenReturn(mockIterable);
        when(mockIterable.into(any())).thenReturn(new ArrayList<>());
        InventoryService.checkOrCreateDBIfNotExists(mongoClientMock);
        verify(mongoDatabaseMock, atLeastOnce()).createCollection(anyString());
    }
}
