package nl.sijpesteijn.lasforce.services;

import nl.sijpesteijn.lasforce.domain.laser.Laser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class AnimationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AnimationRunner.class);
    @Inject
    private Laser laser;

    public void playAnimation(AnimationMetaData animationMetaData) {
        LOG.debug("Starting animation:" + animationMetaData.getName());
//        try {
////            laser.playAnimation(animationInfo);
//        } catch (LaserException e) {
//            e.printStackTrace();
//        }
    }
}
