package nl.sijpesteijn.lasforce.domain.laser;

import com.google.inject.Inject;
import nl.sijpesteijn.lasforce.domain.laser.responses.MessageResponse;
import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Gijs Sijpesteijn
 */
public class LaForceLaserMonitor implements Runnable {
    private Configuration configuration;
    private LaserMonitor laserMonitor;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public LaForceLaserMonitor(Configuration configuration, LaserMonitor laserMonitor) {
        this.configuration = configuration;
        this.laserMonitor = laserMonitor;
    }

    @Override
    public void run() {
        Socket client = null;
        try {
            client = new Socket(configuration.getString("bb.hostname"), configuration.getInt("bb.monitor_port"));
            DataInputStream in = new DataInputStream(client.getInputStream());
            while(true) {
                String socketResponseJson;
                while ((socketResponseJson = in.readLine()) != null) {
                    break;
                }
                if (socketResponseJson != null) {
                    MessageResponse socketResponse = objectMapper.readValue(socketResponseJson, MessageResponse.class);
                    laserMonitor.socketMessage(socketResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
