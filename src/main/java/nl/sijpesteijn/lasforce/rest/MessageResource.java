package nl.sijpesteijn.lasforce.rest;


import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@ServerEndpoint("/ws/message")
public class MessageResource {
    private static Logger LOGGER = Logger.getLogger(MessageResource.class);
    private List<Session> sessions = new ArrayList();

    public MessageResource() throws InterruptedException {
        while (true) {
            informClients();
            Thread.sleep(3000);
        }
    }

    @OnOpen
    public void connect(Session session) {
        this.sessions.add(session);
        LOGGER.debug("Session added.");
    }

    @OnClose
    public void close(Session session, CloseReason closeReason) {
        LOGGER.debug(closeReason.toString());
        this.sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String msg) {

    }

    private void informClients() {
        for(Session session : sessions) {
            session.getAsyncRemote().sendText("Bla");
        }
    }
}
