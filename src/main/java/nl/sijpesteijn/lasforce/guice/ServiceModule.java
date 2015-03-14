package nl.sijpesteijn.lasforce.guice;

import com.google.inject.AbstractModule;
import nl.sijpesteijn.lasforce.domain.laser.LasForceLaser;
import nl.sijpesteijn.lasforce.domain.laser.Laser;
import nl.sijpesteijn.lasforce.services.*;

/** Service Module. */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(Laser.class).to(LasForceLaser.class);
        bind(IAnimationRunner.class).to(AnimationRunner.class);
        bind(IAnimationService.class).to(AnimationService.class);
        bind(ISequenceService.class).to(SequenceService.class);
        bind(IPlaylistService.class).to(PlaylistService.class);
    }
}
