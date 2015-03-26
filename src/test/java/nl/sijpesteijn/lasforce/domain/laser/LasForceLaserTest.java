package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Command;
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
    public void testSendCommand() throws Exception {
        laser.sendCommand(new Command("stop_service"));
    }

    @Test
    public void testPlayAnimation() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(1);
        animation.setLoopCount(5);
        laser.playAnimation(animation);
    }

    @Test
    public void testPlayAnimations() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(24);
        for(int i = 0;i<15;i++) {
            animation.setId(i);
            laser.playAnimation(animation);
            Thread.sleep(1000);
        }
    }

    @Test
    public void testPlaySequence() throws Exception {
        AnimationInfo animation1 = new AnimationInfo();
        animation1.setId(1);
        animation1.setName("PeaceDove8");
        animation1.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation1.setFrameRate(24);
        animation1.setLoopCount(6);
        AnimationInfo animation2 = new AnimationInfo();
        animation2.setId(2);
        animation2.setName("bang1");
        animation2.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation2.setFrameRate(24);
        animation2.setLoopCount(2);

        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("kerst");
        sequence.setAnimations(Arrays.asList(animation1, animation2));
        laser.playSequence(sequence);
    }
}