package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.Ilda;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.PlaySequence;
import nl.sijpesteijn.lasforce.domain.laser.commands.Request;
import nl.sijpesteijn.lasforce.domain.laser.commands.StoreAnimationDataRequest;
import nl.sijpesteijn.lasforce.domain.laser.responses.MessageResponse;
import nl.sijpesteijn.lasforce.domain.laser.responses.OkResponse;
import nl.sijpesteijn.lasforce.domain.laser.responses.SendAnimationDataResponse;
import nl.sijpesteijn.lasforce.domain.laser.responses.SocketResponse;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.services.AnimationMetaData;
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
public class LasForceLaser implements Laser, LaserMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LasForceLaser.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Configuration configuration;

    @Inject
    public LasForceLaser(Configuration configuration) throws Exception {
        this.configuration = configuration;
        LasForceLaserMonitor monitor = new LasForceLaserMonitor(configuration, this);
        Thread laserMonitor = new Thread(monitor);
        laserMonitor.start();
    }

    @Override
    public SocketResponse playSequence(SequenceInfo sequenceInfo) throws LaserException {
        return play(new PlaySequence(sequenceInfo));
    }

    @Override
    public SocketResponse sendCommand(Request request) throws LaserException{
       return play(request);
    }

    private SocketResponse play(Request request) throws LaserException {
        SocketResponse socketResponse = null;
        try {
            Socket client = new Socket(configuration.getString("bb.hostname"), configuration.getInt("bb.communication_port"));
            LOGGER.debug("Connected to server: " + client.getRemoteSocketAddress());
            PrintStream printStream = new PrintStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());

            sendMessage(printStream, request);
            socketResponse = getSocketResponse(in);
            if (continueCommunication(socketResponse)) {
                return handleResponse(printStream, in, socketResponse);
            }
            in.close();
            printStream.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socketResponse;
    }

    private boolean continueCommunication(SocketResponse socketResponse) {
        if (socketResponse instanceof OkResponse) {
//            OkResponse okResponse = (OkResponse) socketResponse;
            return false; //!okResponse.getOriginalRequest().equals(request.getCommand());
        }
        return true;
    }

    private SocketResponse handleResponse(PrintStream printStream, DataInputStream in, SocketResponse socketResponse) throws IOException, URISyntaxException {
        if (socketResponse instanceof SendAnimationDataResponse) {
            SendAnimationDataResponse sdr = (SendAnimationDataResponse) socketResponse;
            IldaReader reader = new IldaReader();
            for(AnimationMetaData animation : sdr.getAnimations()) {
                Ilda ilda = reader.read(new File("./src/main/resources/examples/" + animation.getName() + ".ild"));
                ilda.setId(animation.getId());
                ilda.setLastUpdate(animation.getLastUpdate());
                StoreAnimationDataRequest sadr = new StoreAnimationDataRequest(new AnimationData(animation, ilda));
                sendMessage(printStream, sadr);
                SocketResponse response = getSocketResponse(in);
                while (continueCommunication(response)) {
                    handleResponse(printStream, in, response);
                }
            }
            return socketResponse; //sadr;
        }
        return socketResponse;
    }

    private void sendMessage(PrintStream printStream, Request request) throws IOException, URISyntaxException {
        String commandJson = objectMapper.writeValueAsString(request);
        if (commandJson.length() > 200) {
            System.out.println("Request: " + commandJson.substring(0,200) + "....");
        } else {
            System.out.println("Request: " + commandJson);
        }
        long start = System.currentTimeMillis();
        printStream.print(getLength(commandJson));
        printStream.print(commandJson);
        printStream.flush();
        System.out.println("Sending msg took: " + (System.currentTimeMillis() - start) + " milliseconds");
    }

    private SocketResponse getSocketResponse(DataInputStream in) throws IOException, URISyntaxException {
        String socketResponseJson;
        while ((socketResponseJson = in.readLine()) != null) {
            break;
        }
        System.out.println("Response: " + socketResponseJson);
        return socketResponseJson != null ? objectMapper.readValue(socketResponseJson, SocketResponse.class) : null;
    }

    private String getLength(String json) {
        int length = json.length();
        String strLen = "000000000000";
        String size = strLen.substring(0, strLen.length() - String.valueOf(length).length()) + length;
        return size;
    }

    @Override
    public void socketMessage(MessageResponse messageResponse) {
        System.out.println("Message received. " + messageResponse);
    }
}
