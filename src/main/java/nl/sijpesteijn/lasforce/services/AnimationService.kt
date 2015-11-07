package nl.sijpesteijn.lasforce.services

import com.mongodb.DBCollection
import nl.sijpesteijn.lasforce.domain.AnimationMetadata
import nl.sijpesteijn.lasforce.domain.LasForceAnimation
import nl.sijpesteijn.lasforce.guice.MongoDBProvider
import org.apache.commons.configuration.Configuration
import java.util.*
import javax.inject.Inject

/**
 * @author Gijs Sijpesteijn
 */
class AnimationService {
    private val collection:DBCollection
    private val dataStore:String
    private val defaultFramerate: Int

    @Inject
    constructor(mongoDBProvider: MongoDBProvider, configuration: Configuration) {
        this.collection = mongoDBProvider.getDB().getCollection("animations")
        this.dataStore = configuration.getString("data_store")
        this.defaultFramerate = configuration.getInt("default_framerate")
    }

    fun getAnimations(): List<AnimationMetadata> {
        val animations = ArrayList<AnimationMetadata>()
        val cursor = collection.find()
        while(cursor.hasNext()) {
            val dbObject = cursor.next()
            val metaData = AnimationMetadata(dbObject.get("_id") as Long,dbObject.get("name") as String,dbObject.get("framerate") as Int)
            animations.add(metaData)
        }
        return animations
    }


    fun getAnimation(id: Long): AnimationMetadata {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getLasForceAnimation(id: Long): LasForceAnimation {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun uploadAnimation(fileName: String?, data: ByteArray?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}