package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.services.AnimationInfo;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class LasForceLaserTest {
    private LasForceLaser laser;

    @Before
    public void setUp() throws Exception {
        Configuration config = new BaseConfiguration();
        config.addProperty("bb.hostName","127.0.0.1");
        config.addProperty("bb.port","5555");
        laser = new LasForceLaser(config);
    }

    @Test
    public void testPlayAnimation() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(24);
        laser.playAnimation(animation);
    }

    @Test
    public void testPlaySequence() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(24);

        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("kerst");
        sequence.setAnimations(Arrays.asList(animation));
        laser.playSequence(sequence);
    }
}