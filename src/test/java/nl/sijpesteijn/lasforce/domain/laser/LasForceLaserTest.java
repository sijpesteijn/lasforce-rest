package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.InstructionRequest;
import nl.sijpesteijn.lasforce.domain.laser.commands.StoreAnimationDataRequest;
import nl.sijpesteijn.lasforce.domain.laser.responses.SocketResponse;
import nl.sijpesteijn.lasforce.services.AnimationMetaData;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;


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
    public void testSendStopInstruction() throws Exception {
        laser.sendCommand(new InstructionRequest("stop"));
    }

    @Test
    public void testSendInstructionListCache() throws Exception {
        SocketResponse socketResponse = laser.sendCommand(new InstructionRequest("list_cache"));
        assertNotNull(socketResponse);
    }

    @Test
    public void testPopAnimation() throws Exception {
        SocketResponse socketResponse = laser.sendCommand(new InstructionRequest("pop_animation", "5675"));
        assertNotNull(socketResponse);
    }

    @Test
    public void testSendAnimationData() throws Exception {
        AnimationMetaData animation = new AnimationMetaData();
        animation.setId(23);
        animation.setName("aristcat");
        animation.setLastUpdate("2015-31-12T00:00:00.000Z");
        animation.setFrameRate(1);
        animation.setLoopCount(1);
        IldaReader reader = new IldaReader();
        IldaFormat ilda = reader.read(new File("./src/main/resources/examples/" + animation.getName() + ".ild"));
        ilda.setId(animation.getId());
        ilda.setLastUpdate(animation.getLastUpdate());
        StoreAnimationDataRequest sadr = new StoreAnimationDataRequest(new AnimationData(animation, ilda));
        SocketResponse socketResponse = laser.sendCommand(sadr);
        assertNotNull(socketResponse);
    }


    @Test
    public void testSimpleSequence() throws Exception {
        AnimationMetaData animation = new AnimationMetaData();
        animation.setId(1);
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-31-12T00:00:00.000Z");
        animation.setFrameRate(1);
        animation.setLoopCount(1);
        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("simple sequence");
        sequence.setLoopCount(1);
        sequence.setAnimations(Arrays.asList(animation));
        SocketResponse socketResponse = laser.playSequence(sequence);
        assertNotNull(socketResponse);
    }

    @Test
    public void testPlaySequence() throws Exception {
        AnimationMetaData animation1 = new AnimationMetaData();
        animation1.setId(5674);
        animation1.setName("PeaceDove8");
        animation1.setLastUpdate("2015-31-12T00:00:00.000Z");
        animation1.setFrameRate(1);
        animation1.setLoopCount(6);
        AnimationMetaData animation2 = new AnimationMetaData();
        animation2.setId(5675);
        animation2.setName("bang1");
        animation2.setLastUpdate("2015-31-12T00:00:00.000Z");
        animation2.setFrameRate(1);
        animation2.setLoopCount(2);

        SequenceInfo sequence = new SequenceInfo();
        sequence.setName("Sequence");
        sequence.setLoopCount(-1);
        sequence.setAnimations(Arrays.asList(animation1, animation2));
        SocketResponse socketResponse = laser.playSequence(sequence);
        assertNotNull(socketResponse);
    }
}