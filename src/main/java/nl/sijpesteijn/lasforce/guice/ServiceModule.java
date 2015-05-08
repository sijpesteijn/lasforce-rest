package nl.sijpesteijn.lasforce.guice;

import com.google.inject.AbstractModule;
import nl.sijpesteijn.lasforce.domain.laser.LasForceLaser;
import nl.sijpesteijn.lasforce.domain.laser.Laser;

/** Service Module. */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(Laser.class).to(LasForceLaser.class);
    }
}
