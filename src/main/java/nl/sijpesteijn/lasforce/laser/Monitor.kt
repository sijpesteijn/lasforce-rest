package nl.sijpesteijn.lasforce.laser

import org.slf4j.LoggerFactory
import java.net.Socket
import java.net.SocketAddress

/**
 * @author Gijs Sijpesteijn
 */
class Monitor(socketAddress: SocketAddress) {
    val LOG = LoggerFactory.getLogger(Monitor::class.java)
    val socket = Socket()
    val socketAddress = socketAddress

    fun isConnected(): Boolean {
        return socket.isConnected
    }

    fun reConnect() {
        if (socket.isConnected) {
            socket.close()
        }
        try {
            socket.connect(socketAddress)
        } catch(e:Exception) {
            LOG.debug(e.message)
            println(e.message)
        }
    }

    fun getStatus():String {
        return "${System.currentTimeMillis()}"
    }


}