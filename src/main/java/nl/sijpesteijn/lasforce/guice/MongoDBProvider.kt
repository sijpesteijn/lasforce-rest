package nl.sijpesteijn.lasforce.guice

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import org.apache.commons.configuration.Configuration
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
class MongoDBProvider: Provider<DB> {
    val client:MongoClient

    @Inject
    constructor(configuration: Configuration) {
        this.client = MongoClient(ServerAddress(configuration.getString("mongo.host"), configuration.getInt("mongo.port")))
    }

    override fun get(): DB {
        return client.getDB("lasforce")
    }

    fun getDB(): DB {
        return get()
    }
}