package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Command;
import nl.sijpesteijn.lasforce.domain.laser.commands.InstructionCommand;
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
    public void testSendInstruction() throws Exception {
        laser.sendCommand(new InstructionCommand("listQueue", ""));
    }

    @Test
    public void testSimpleSequence() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(1);
        animation.setLoopCount(1);
        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("simple sequence");
        sequence.setLoopCount(1);
        sequence.setAnimations(Arrays.asList(animation));
        laser.playSequence(sequence);
    }

    @Test
    public void testPlaySequence() throws Exception {
        AnimationInfo animation1 = new AnimationInfo();
        animation1.setId(5674);
        animation1.setName("PeaceDove8");
        animation1.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation1.setFrameRate(24);
        animation1.setLoopCount(6);
        AnimationInfo animation2 = new AnimationInfo();
        animation2.setId(5675);
        animation2.setName("bang1");
        animation2.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation2.setFrameRate(24);
        animation2.setLoopCount(2);

        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("Sequence");
        sequence.setLoopCount(2);
        sequence.setAnimations(Arrays.asList(animation1, animation2));
        for(int i = 0; i< 1;i++) {
            laser.playSequence(sequence);
//            Thread.sleep(1000);
        }
    }
}