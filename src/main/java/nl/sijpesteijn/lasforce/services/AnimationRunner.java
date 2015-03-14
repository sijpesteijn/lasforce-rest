package nl.sijpesteijn.lasforce.services;

import nl.sijpesteijn.lasforce.domain.laser.Laser;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class AnimationRunner implements IAnimationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AnimationRunner.class);
    @Inject
    private Laser laser;

    @Override
    public void playAnimation(AnimationInfo animationInfo) {
        LOG.debug("Starting animation:" + animationInfo.getName());
        try {
            laser.playAnimation(animationInfo);
        } catch (LaserException e) {
            e.printStackTrace();
        }
    }
}
