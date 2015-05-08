package nl.sijpesteijn.lasforce.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import nl.sijpesteijn.ilda.Ilda;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.Animation;
import nl.sijpesteijn.lasforce.domain.LasForceAnimation;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.laser.IldaToLasForceConverter;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
public class AnimationService {
    private final String dataStore;
    private final int defaultFramerate;
    private IldaReader reader = new IldaReader();
    private Morphia morphia = new Morphia();
    private Datastore lasforce;

    @Inject
    public AnimationService(Provider<MongoClient> mongoProvider, Configuration configuration) {
        this.dataStore = configuration.getString("data_store");
        this.defaultFramerate = configuration.getInt("default_framerate");
        lasforce = morphia.createDatastore(mongoProvider.get(), "lasforce");
    }

    public Animation getAnimation(final long id) throws LaserException {
        Animation animation = lasforce.find(Animation.class).field("_id").equal(id).get();
        return animation;
    }

    public List<Animation> getAnimations() throws LaserException {
        List<Animation> animations = lasforce.find(Animation.class).asList();
        return animations;
    }


    public Animation addAnimation(Animation animation) throws LaserException {
        long id = new Date().getTime();
        BasicDBObject dbObject = new BasicDBObject("_id", id)
                .append("name", animation.getName())
                .append("lastUpdate", id)
                .append("frameRate", animation.getFrameRate())
                .append("fileName", animation.getFileName());

        lasforce.save(dbObject);
        animation.setId(id);
        animation.setLastUpdate(id);
        return animation;
    }

    public void update(final Animation animation) throws LaserException {
    }

    public Animation uploadAnimation(String fileName, byte[] data) throws LaserException {
        Animation animation = new Animation();
        try {
            FileUtils.writeByteArrayToFile(new File(this.dataStore + fileName), data);
            animation.setName(fileName);
            animation.setFrameRate(defaultFramerate);
            animation.setFileName(fileName);
            animation = addAnimation(animation);
            return animation;
        } catch (IOException e) {
            throw new LaserException(e);
        }
    }

    public LasForceAnimation getLasForceAnimation(long id) throws LaserException {
        Animation animation = getAnimation(id);
        try {
            Ilda ilda = reader.read(new File(this.dataStore + animation.getFileName()));
            IldaToLasForceConverter converter = new IldaToLasForceConverter();
            AnimationMetaData metaData = new AnimationMetaData();
            metaData.setFrameRate(animation.getFrameRate());
            metaData.setName(animation.getName());
            LasForceAnimation lasForceAnimation = converter.convert(ilda);
            lasForceAnimation.setMetaData(metaData);
            return lasForceAnimation;
        } catch (IOException e) {
            throw new LaserException(e);
        } catch (URISyntaxException e) {
            throw new LaserException(e);
        }
    }

}