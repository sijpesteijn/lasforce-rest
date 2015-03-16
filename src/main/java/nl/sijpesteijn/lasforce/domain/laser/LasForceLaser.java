package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Command;
import nl.sijpesteijn.lasforce.domain.laser.commands.PlayAnimation;
import nl.sijpesteijn.lasforce.domain.laser.commands.PlaySequence;
import nl.sijpesteijn.lasforce.domain.laser.commands.SendAnimationData;
import nl.sijpesteijn.lasforce.domain.laser.responses.OkResponse;
import nl.sijpesteijn.lasforce.domain.laser.responses.SocketResponse;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.services.AnimationInfo;
import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URISyntaxException;

/**
 * @author Gijs Sijpesteijn
 */
public class LasForceLaser implements Laser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LasForceLaser.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Socket client;
    private DataInputStream in;
    private PrintStream printStream;

    @Inject
    public LasForceLaser(Configuration configuration) throws IOException {
        client = new Socket(configuration.getString("bb.hostname"), configuration.getInt("bb.port"));
        LOGGER.debug("Connected to server: " + client.getRemoteSocketAddress());
        printStream = new PrintStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());
    }

    @Override
    public void playAnimation(AnimationInfo animationInfo) throws LaserException {
        try {
            PlayAnimation paCommand = new PlayAnimation(animationInfo);
            SocketResponse socketResponse = sendMessage(paCommand);
            while(!(socketResponse instanceof OkResponse)) {

                // If animation_data then send the ilda data
                IldaReader reader = new IldaReader();
                IldaFormat ilda = null;
                ilda = reader.read(new File("./src/main/resources/examples/astroid.ild"));
                SendAnimationData sad = new SendAnimationData(new AnimationData(ilda));
                socketResponse = sendMessage(sad);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private SocketResponse sendMessage(Command command) throws IOException, URISyntaxException {
        String commandJson = objectMapper.writeValueAsString(command);
        if (commandJson.length() > 200) {
            System.out.println(commandJson.substring(0,200) + "....");
        } else {
            System.out.println(commandJson);
        }
        long start = System.currentTimeMillis();
        printStream.print(getLength(commandJson));
        printStream.print(commandJson);
        printStream.flush();
        System.out.println("Sending msg took: " + (System.currentTimeMillis() - start) + " milliseconds");
        return getResponseCommand(in);
    }

    private SocketResponse getResponseCommand(DataInputStream in) throws IOException, URISyntaxException {
        String socketResponseJson;
        while ((socketResponseJson = in.readLine()) != null) {
            break;
        }
        System.out.println("Response command: " + socketResponseJson);
        return getSocketResponse(socketResponseJson);
    }

    private SocketResponse getSocketResponse(String commandJson) throws IOException, URISyntaxException {

        return objectMapper.readValue(commandJson, SocketResponse.class);
    }


    @Override
    public void playSequence(SequenceInfo sequenceInfo) throws LaserException {
        PlaySequence playSequence = new PlaySequence(sequenceInfo);
        try {
            sendMessage(playSequence);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String getLength(String json) {
        int length = json.length();
        String strLen = "000000000000";
        String size = strLen.substring(0, strLen.length() - String.valueOf(length).length()) + length;
        return size;
    }
}
