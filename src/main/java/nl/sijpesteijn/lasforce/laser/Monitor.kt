package nl.sijpesteijn.lasforce.laser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sijpesteijn.lasforce.laser.request.InstructionRequest
import nl.sijpesteijn.lasforce.laser.request.Request
import nl.sijpesteijn.lasforce.laser.response.Response
import org.slf4j.LoggerFactory
import java.io.DataInputStream
import java.io.PrintStream
import java.net.Socket
import java.net.SocketAddress

/**
 * @author Gijs Sijpesteijn
 */
class Monitor(socketAddress: SocketAddress) {
    val LOG = LoggerFactory.getLogger(Monitor::class.java)
    val socket = Socket()
    val socketAddress = socketAddress
    val mapper = jacksonObjectMapper()

    fun isConnected(): Boolean {
        return socket.isConnected
    }

    fun reConnect() {
        if (socket.isConnected) {
            socket.close()
        }
        try {
            socket.connect(socketAddress)
            LOG.debug("Connected to server: ${socket.getRemoteSocketAddress()}")
        } catch(e:Exception) {
            LOG.debug(e.message)
            println(e.message)
        }
    }

    fun getStatus():String {
        sendMessage(InstructionRequest("list_cache"))
        val socketResponse = getSocketResponse()
        return "${socketResponse.name}"
    }

    fun sendMessage(request: Request) {
        val message = mapper.writeValueAsString(request)
        val printStream = PrintStream(socket.getOutputStream())
        printStream.print(getLength(message))
        printStream.print(message);
        printStream.flush();
    }

    fun getSocketResponse() : Response {
        val dataInputString = DataInputStream(socket.getInputStream())
        val socketResponseJson = dataInputString.readLine();
        println("Response: " + socketResponseJson)
        return mapper.readValue(socketResponseJson, Response::class.java)
    }

    fun getLength(message:String):String {
        val length = message.length
        val strLen = "000000000000"
        val size = strLen.substring(0, strLen.length - length.toString().length) + length
        return size
    }

}