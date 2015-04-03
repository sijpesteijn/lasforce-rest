package nl.sijpesteijn.lasforce.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.*;
import com.mongodb.util.JSON;
import nl.sijpesteijn.lasforce.domain.Animation;
import nl.sijpesteijn.lasforce.domain.LaserAnimation;
import nl.sijpesteijn.lasforce.domain.MongoToAnimationSerializer;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.exceptions.LaserException;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
public class AnimationService implements IAnimationService {
    @Inject
    private Provider<MongoClient> mongoProvider;

    @Override
    public Animation load(final String name) throws IOException, URISyntaxException {
        DB db = mongoProvider.get().getDB("lasforce");
        DBCollection animations = db.getCollection("animations");
        DBObject dbObject = animations.findOne(new BasicDBObject("name", name));
        String json = JSON.serialize(dbObject);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LaserAnimation.class, new MongoToAnimationSerializer());
        Gson gson = builder.create();
        LaserAnimation animation = gson.fromJson(json, LaserAnimation.class);
        return animation;
    }

    @Override
    public List<AnimationMetaData> getAnimations() {
        DB db = mongoProvider.get().getDB("lasforce");
        List<AnimationMetaData> animations = new ArrayList();
        DBCollection collection = db.getCollection("animations");
        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            DBObject next = cursor.next();
            AnimationMetaData info = new AnimationMetaData();
            info.setName((String) next.get("name"));
            info.setLastUpdate((String) next.get("lastUpdate"));
            animations.add(info);
        }
        return animations;
    }

    @Override
    public void save(final Animation animation) throws LaserException {
        DB db = mongoProvider.get().getDB("lasforce");
        DBCollection animations = db.getCollection("animations");
        String json = new Gson().toJson(animation);
        DBObject dbObject = (DBObject) JSON.parse(json);
        animations.save(dbObject);
    }

    @Override
    public List<AnimationMetaData> getAnimations(SequenceInfo sequenceInfo) {
        return null;
    }
}