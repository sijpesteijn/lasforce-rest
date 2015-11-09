package nl.sijpesteijn.lasforce.laser

import org.eclipse.jetty.websocket.WebSocket

/**
 * @author Gijs Sijpesteijn
 */
class LaserMonitorConnection(connection: WebSocket.Connection, monitor: Monitor) : Runnable {
    val monitor = monitor
    val connection = connection

    override fun run() {
        while(true) {
            if (monitor.isConnected()) {
                connection.sendMessage(monitor.getStatus())
            } else {
                monitor.reConnect();
            }
            Thread.sleep(1000);
        }
    }

}