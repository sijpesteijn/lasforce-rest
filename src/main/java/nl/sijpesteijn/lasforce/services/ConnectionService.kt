package nl.sijpesteijn.lasforce.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.inject.Inject
import nl.sijpesteijn.lasforce.laser.laserRequests.LaserRequest
import nl.sijpesteijn.lasforce.laser.laserResponses.InfoResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.LaserResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.NotConnectedResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.OkResponse
import org.apache.commons.configuration.Configuration
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket

/**
 * @author Gijs Sijpesteijn
 */

class ConnectionService {
    val LOG = LoggerFactory.getLogger(ConnectionService::class.java)
    val hostname: String
    val port: Int
    var socket: Socket? = null
    val mapper = jacksonObjectMapper()

    @Inject constructor(configuration: Configuration) {
        hostname = configuration.getString("bb.hostname")
        port = configuration.getInt("bb.communication_port")
    }

    fun connect() {
        try {
            socket = Socket(hostname, port)
            LOG.debug("Connected to server: ${socket?.getRemoteSocketAddress()}")
        } catch(e:Exception) {
            LOG.debug(e.message)
        }
    }

    fun isConnected(): Boolean {
        return socket?.isConnected ?: false
    }

    fun close() {
        socket?.close()
    }

    fun reconnect() : Boolean {
        close()
        connect()
        return isConnected()
    }

    fun getLength(message:String):String {
        var length = "000000000000" + message.length
        return length.substring(length.length-12, length.length)
    }

    fun execute(request: LaserRequest): LaserResponse {
        if (socket != null) {
            val message = mapper.writeValueAsString(request)
            val printStream = PrintStream(socket?.outputStream)
            printStream.print(getLength(message))
            printStream.print(message)
            printStream.flush()
            println(message)
//            if (request is InfoRequest) {
//                return getInfoResponse()
//            }
            return OkResponse()
        }
        return NotConnectedResponse()
    }

    private fun getInfoResponse(): LaserResponse {
        val isr = BufferedReader(InputStreamReader(socket?.inputStream))
        var line = isr.readLine()
        while(line != null) {
            println("Line: " + line);
            line = isr.readLine()
        }
        return InfoResponse(info="")
    }

}