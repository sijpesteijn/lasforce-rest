package nl.sijpesteijn.lasforce.websocket

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

class LaserStatusWebSocket(monitor: Monitor) :WebSocket.OnTextMessage,WebSocket.Connection {
    val connections = ArrayList<WebSocket.Connection>()
    val monitor = monitor
    val LOG = LoggerFactory.getLogger(LaserStatusWebSocket::class.java);

    override fun onOpen(connection: WebSocket.Connection) {
        LOG.debug("Connection added ${connection}")
        LaserMonitorConnection(connection, monitor).run()
        connections.add(connection);
    }

    override fun onMessage(message: String?) {
        throw UnsupportedOperationException()
    }

    override fun close() {
        throw UnsupportedOperationException()
    }

    override fun close(status: Int, message: String?) {
        throw UnsupportedOperationException()
    }

    override fun disconnect() {
        throw UnsupportedOperationException()
    }

    override fun getMaxBinaryMessageSize(): Int {
        throw UnsupportedOperationException()
    }

    override fun getMaxIdleTime(): Int {
        throw UnsupportedOperationException()
    }

    override fun getMaxTextMessageSize(): Int {
        throw UnsupportedOperationException()
    }

    override fun getProtocol(): String? {
        throw UnsupportedOperationException()
    }

    override fun isOpen(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun sendMessage(message: ByteArray?, p1: Int, p2: Int) {
        throw UnsupportedOperationException()
    }

    override fun sendMessage(message: String?) {
        throw UnsupportedOperationException()
    }

    override fun setMaxBinaryMessageSize(size: Int) {
        throw UnsupportedOperationException()
    }

    override fun setMaxIdleTime(idleTime: Int) {
        throw UnsupportedOperationException()
    }

    override fun setMaxTextMessageSize(size: Int) {
        throw UnsupportedOperationException()
    }

    override fun onClose(status: Int, message: String?) {
        LOG.debug("Closing ${status} ${message}")
    }

}