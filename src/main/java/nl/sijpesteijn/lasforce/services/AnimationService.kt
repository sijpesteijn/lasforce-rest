package nl.sijpesteijn.lasforce.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBObject
import nl.sijpesteijn.ilda.IldaReader
import nl.sijpesteijn.lasforce.IldaConverter
import nl.sijpesteijn.lasforce.domain.Animation
import nl.sijpesteijn.lasforce.guice.MongoDBProvider
import org.apache.commons.configuration.Configuration
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * @author Gijs Sijpesteijn
 */
class AnimationService {
    private val collection: DBCollection
    private val dataStore: String
    private val defaultFramerate: Int
    private val mapper = jacksonObjectMapper()

    @Inject
    constructor(mongoDBProvider: MongoDBProvider, configuration: Configuration) {
        this.collection = mongoDBProvider.getDB().getCollection("animations")
        this.dataStore = configuration.getString("data_store")
        this.defaultFramerate = configuration.getInt("default_framerate")
    }

    fun getAnimationsMetadata(): List<Animation> {
        val animations = ArrayList<Animation>()
        val cursor = collection.find()
        while (cursor.hasNext()) {
            val dbObject = cursor.next()
            val animation = loadMetadata(dbObject)
            animations.add(animation)
        }
        return animations
    }

    fun getAnimationMetadata(id: Long): Animation {
        val dbObject = collection.findOne(BasicDBObject("_id", id))
        return loadMetadata(dbObject)
    }

    private fun loadMetadata(dbObject:DBObject) : Animation {
        val id = dbObject.get("_id") as Long
        val name = dbObject.get("name") as String
        val last_update = dbObject.get("last_update") as Long
        val repeat = dbObject.get("repeat") as Int
        val total_frames = dbObject.get("total_frames") as Int
        val animation = Animation(id, name, last_update, repeat, total_frames)
        return animation
    }

    fun getLasForceAnimation(id: Long): Animation {
        val animation: Animation = mapper.readValue(FileUtils.readFileToString(File(this.dataStore + id + ".las")))
        return animation
    }

    fun uploadAnimation(fileName: String, data: ByteArray?) {
        val name = fileName.split(".")[0];
        val convert = IldaConverter()
        val reader = IldaReader()
        val ilda = reader.read(data)
        val frames = convert.fromIlda(ilda)
        val animation = Animation(System.currentTimeMillis(), name, System.currentTimeMillis(), 1, frames.size, 1000, frames)
        saveLasforceAnimation(animation)
    }

    fun saveLasforceAnimation(animation: Animation): Animation {
        val new_animation = saveAnimationMetadata(animation)
        mapper.writeValue(File(this.dataStore + new_animation.id + ".las"), animation)
        return new_animation
    }

    fun saveAnimationMetadata(animationMetadata: Animation): Animation {
        animationMetadata.last_update = System.currentTimeMillis()
        val dbObject = BasicDBObject("_id", animationMetadata.id)
                .append("name", animationMetadata.name)
                .append("last_update", animationMetadata.last_update)
                .append("total_frames", animationMetadata.total_frames)
                .append("repeat", animationMetadata.repeat)
        collection.save(dbObject)
        return animationMetadata
    }

    fun deleteAnimation(id: Long) {
        File(this.dataStore + "${id}.las").delete();
        collection.remove(BasicDBObject("_id", id));
    }
}