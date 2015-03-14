package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
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

/**
 * @author Gijs Sijpesteijn
 */
public class LasForceLaser implements Laser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LasForceLaser.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Socket client;

    @Inject
    public LasForceLaser(Configuration configuration) throws IOException {
        client = new Socket(configuration.getString("bb.hostname"), configuration.getInt("bb.port"));
        LOGGER.debug("Connected to server: " + client.getRemoteSocketAddress());
    }

    @Override
    public void playAnimation(AnimationInfo animationInfo) throws LaserException {
        try {
            PrintStream printStream = new PrintStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            String animation = objectMapper.writeValueAsString(animationInfo);
            printStream.print(getLength(animation));
            printStream.print(animation);
            printStream.flush();
            String response = getResponse(in);
            System.out.println("Res: " + response);

            IldaReader reader = new IldaReader();
            IldaFormat content = reader.read(new File("src/main/resources/examples/" + animationInfo.getName() + ".ild"));
            content.getHeader().setFrameRate(animationInfo.getFrameRate());

            long start = System.currentTimeMillis();
            String json = objectMapper.writeValueAsString(content);
            printStream.print(getLength(json));
            printStream.print(json);
            printStream.flush();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                if (inputLine.equals("OK"))
                    break;
            }
            long end = System.currentTimeMillis();
            System.out.println("Took: " + (end - start) + " milliseconds");
            printStream.close();
            in.close();
            client.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    private String getResponse(DataInputStream in) throws IOException {
//        if ( in.available() > 0)
        return in.readUTF();
    }

    @Override
    public void playSequence(SequenceInfo sequenceInfo) throws LaserException {

    }

    private String getLength(String json) {
        int length = json.length();
        String strLen = "000000000000";
        String size = strLen.substring(0, strLen.length() - String.valueOf(length).length()) + length;
        System.out.println(size);
        return size;
    }
}
