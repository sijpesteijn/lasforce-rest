package nl.sijpesteijn.lasforce.guice;

import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.UnknownHostException;

@Singleton
public class MongoDBProvider implements Provider<MongoClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBProvider.class);
    private MongoClient client;

    public MongoClient get() {
        if (client == null) {
            try {
                client = new MongoClient(new ServerAddress("localhost", 27017));
            } catch (UnknownHostException e) {
                LOGGER.debug("Could not connect to mongo: %s", e.getMessage());
            }
        }
        return client;
    }
}