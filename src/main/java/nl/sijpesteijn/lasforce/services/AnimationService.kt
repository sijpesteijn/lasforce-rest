package nl.sijpesteijn.lasforce.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import nl.sijpesteijn.ilda.IldaReader
import nl.sijpesteijn.lasforce.IldaConverter
import nl.sijpesteijn.lasforce.domain.AnimationMetadata
import nl.sijpesteijn.lasforce.domain.Frame
import nl.sijpesteijn.lasforce.domain.LasForceAnimation
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
    private val collection:DBCollection
    private val dataStore:String
    private val defaultFramerate: Int
    private val mapper = jacksonObjectMapper()

    @Inject
    constructor(mongoDBProvider: MongoDBProvider, configuration: Configuration) {
        this.collection = mongoDBProvider.getDB().getCollection("animations")
        this.dataStore = configuration.getString("data_store")
        this.defaultFramerate = configuration.getInt("default_framerate")
    }

    fun getAnimationsMetadata(): List<AnimationMetadata> {
        val animations = ArrayList<AnimationMetadata>()
        val cursor = collection.find()
        while(cursor.hasNext()) {
            val dbObject = cursor.next()
            val metaData = AnimationMetadata(dbObject.get("_id") as Long,dbObject.get("name") as String,dbObject.get("framerate") as Int)
            animations.add(metaData)
        }
        return animations
    }


    fun getAnimationMetadata(id: Long): AnimationMetadata {
        val dbObject = collection.findOne(BasicDBObject("_id", id))
        val metaData = AnimationMetadata(dbObject.get("_id") as Long,dbObject.get("name") as String,dbObject.get("framerate") as Int)
        return metaData
    }

    fun getLasForceAnimation(id: Long): LasForceAnimation {
        val metadata = getAnimationMetadata(id)
        val frames : List<Frame> = mapper.readValue(FileUtils.readFileToString(File(this.dataStore + metadata.id + ".las")))
        val lasForceAnimation = LasForceAnimation(metadata, frames)
        return lasForceAnimation
    }

    fun uploadAnimation(fileName: String, data: ByteArray?) {
        val name = fileName.split(".")[0];
        val metadata = AnimationMetadata(System.currentTimeMillis(), name, this.defaultFramerate)
        val convert = IldaConverter()
        val reader = IldaReader()
        val ilda = reader.read(data)
        val frames = convert.fromIlda(ilda)
        val lasforceAnimation = LasForceAnimation(metadata, frames)
        saveLasforceAnimation(lasforceAnimation)
    }

    fun saveLasforceAnimation(lasForceAnimation: LasForceAnimation):LasForceAnimation {
        var metadata = lasForceAnimation.metadata
        metadata = saveAnimationMetadata(metadata)
        mapper.writeValue(File(this.dataStore + metadata.id + ".las"), lasForceAnimation.frames)
        return lasForceAnimation
    }

    fun saveAnimationMetadata(animationMetadata: AnimationMetadata):AnimationMetadata {
        animationMetadata.lastUpdate = System.currentTimeMillis()
        val dbObject = BasicDBObject("_id", animationMetadata.id).append("name",animationMetadata.name).append("framerate",animationMetadata.framerate).append("lastupdate",animationMetadata.lastUpdate)
        collection.save(dbObject)
        return animationMetadata
    }

    fun deleteAnimation(id: Long) {
        File(this.dataStore + "${id}.las").delete();
        collection.remove(BasicDBObject("_id", id));
    }
}