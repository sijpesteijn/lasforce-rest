package nl.sijpesteijn.lasforce.websocket;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class MessageWebSocketServlet extends WebSocketServlet {
    private static Logger LOGGER = Logger.getLogger(MessageWebSocketServlet.class);

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
        return new MessageWebSocket();
    }

    private class MessageWebSocket implements WebSocket {
        private List<Connection> connections = new ArrayList();

        public MessageWebSocket() {
            while (true) {
                informClients();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onOpen(Connection connection) {
            this.connections.add(connection);
            LOGGER.debug("Connection added.");
        }

        @Override
        public void onClose(int i, String s) {
            LOGGER.debug(s);
            this.connections.remove(i);

        }

        private void informClients() {
            for(Connection connection : connections) {
                try {
                    connection.sendMessage("Bla");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
