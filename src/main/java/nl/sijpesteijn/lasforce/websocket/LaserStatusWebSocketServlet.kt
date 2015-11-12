package nl.sijpesteijn.lasforce.websocket

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sijpesteijn.lasforce.laser.LaserMonitorConnection
import nl.sijpesteijn.lasforce.laser.Monitor
import org.eclipse.jetty.websocket.WebSocket
import org.eclipse.jetty.websocket.WebSocketServlet
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * @author Gijs Sijpesteijn
 */
class LaserStatusWebSocketServlet : WebSocketServlet() {
    var monitor = Monitor(InetSocketAddress("127.0.0.1",5556))

    override fun doWebSocketConnect(servletRequest: HttpServletRequest?, protocol: String?): WebSocket? {
        return LaserStatusWebSocket(monitor)
    }
}

class LaserStatusWebSocket(monitor: Monitor) :WebSocket.OnBinaryMessage,WebSocket.OnTextMessage {
    val connections = ArrayList<LaserMonitorConnection>()
    val monitor = monitor
    val LOG = LoggerFactory.getLogger(LaserStatusWebSocket::class.java)
    val mapper = jacksonObjectMapper()

    override fun onOpen(connection: WebSocket.Connection) {
        println("Connection added ${connection}")
        val laserMonitorConnection = LaserMonitorConnection(connection, monitor)
//        laserMonitorConnection.run()
        connections.add(laserMonitorConnection);
    }

    override fun onMessage(message: String?) {
        println("Msg: ${message}")
    }

    override fun onMessage(data: ByteArray?, offset: Int, length: Int) {
//        val frameId = mapper.readValue(data,offset,length,FrameId::class.java)
//        println("MSG: $frameId.frameId")
        println("MSG BIN")
    }

    override fun onClose(status: Int, message: String?) {
        LOG.debug("Closing ${status} ${message}")
    }

}