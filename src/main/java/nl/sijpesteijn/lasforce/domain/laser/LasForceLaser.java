package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Command;
import nl.sijpesteijn.lasforce.domain.laser.commands.PlayAnimation;
import nl.sijpesteijn.lasforce.domain.laser.commands.PlaySequence;
import nl.sijpesteijn.lasforce.domain.laser.commands.SendAnimationData;
import nl.sijpesteijn.lasforce.domain.laser.responses.AnimationRequestResponse;
import nl.sijpesteijn.lasforce.domain.laser.responses.ErrorResponse;
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
import java.util.Date;

/**
 * @author Gijs Sijpesteijn
 */
public class LasForceLaser implements Laser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LasForceLaser.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Socket client;
    private DataInputStream in;
    private PrintStream printStream;
    private Configuration configuration;

    @Inject
    public LasForceLaser(Configuration configuration) throws IOException {
        this.configuration = configuration;
    }

    @Override
    public void playAnimation(AnimationInfo animationInfo) throws LaserException {
        play(new PlayAnimation(animationInfo));
    }

    @Override
    public void playSequence(SequenceInfo sequenceInfo) throws LaserException {
        play(new PlaySequence(sequenceInfo));
    }

    @Override
    public void sendCommand(Command command) throws LaserException{
        play(command);
    }

    private void play(Command command) throws LaserException {
        try {
            client = new Socket(configuration.getString("bb.hostname"), configuration.getInt("bb.port"));
            LOGGER.debug("Connected to server: " + client.getRemoteSocketAddress());
            printStream = new PrintStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            sendMessage(printStream, command);
            handleResponse(getResponseCommand(in));
            in.close();
            printStream.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
    private void handleResponse(SocketResponse socketResponse) throws IOException, URISyntaxException {
        if (socketResponse == null) {
            return;
        }
        if(socketResponse instanceof AnimationRequestResponse) {
           AnimationRequestResponse arr = (AnimationRequestResponse) socketResponse;
            for(AnimationInfo animationInfo : arr.getAnimations()) {
                IldaReader reader = new IldaReader();
                IldaFormat ilda = reader.read(new File("./src/main/resources/examples/" + animationInfo.getName() + ".ild"));
                ilda.setId(1);
                ilda.setLastUpdate(new Date());
                SendAnimationData sad = new SendAnimationData(new AnimationData(animationInfo, ilda));
                sendMessage(printStream, sad);
                SocketResponse response = getResponseCommand(in);
                while(!(response instanceof OkResponse)) {
                    handleResponse( response);
                    response = getResponseCommand(in);
                }
            }
        }
        if (socketResponse instanceof OkResponse) {
            LOGGER.debug("Ok response received.");
        }
        if (socketResponse instanceof ErrorResponse) {
            LOGGER.debug("Error response received %s",socketResponse.toString());
        }
    }

    private void sendMessage(PrintStream printStream, Command command) throws IOException, URISyntaxException {
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
    }

    private SocketResponse getResponseCommand(DataInputStream in) throws IOException, URISyntaxException {
        String socketResponseJson;
        while ((socketResponseJson = in.readLine()) != null) {
            break;
        }
        System.out.println("Response command: " + socketResponseJson);
        return socketResponseJson != null ? objectMapper.readValue(socketResponseJson, SocketResponse.class) : null;
    }

    private String getLength(String json) {
        int length = json.length();
        String strLen = "000000000000";
        String size = strLen.substring(0, strLen.length() - String.valueOf(length).length()) + length;
        return size;
    }
}
