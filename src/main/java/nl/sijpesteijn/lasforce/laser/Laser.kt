package nl.sijpesteijn.lasforce.laser

import nl.sijpesteijn.lasforce.laser.laserRequests.ConnectRequest
import nl.sijpesteijn.lasforce.laser.laserRequests.LaserRequest
import nl.sijpesteijn.lasforce.laser.laserResponses.ConnectResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.LaserResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.OkResponse
import nl.sijpesteijn.lasforce.services.ConnectionService
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Gijs Sijpesteijn
 */
interface Laser {
    fun isConnected(): Boolean

    fun reConnect(): Boolean

    fun execute(request: LaserRequest): LaserResponse
}

@Singleton
class LaserMock() : Laser {
    override fun isConnected(): Boolean {
        return false
    }

    override fun reConnect(): Boolean {
        return true
    }

    override fun execute(request: LaserRequest): LaserResponse {
        return OkResponse();
    }
}

@Singleton
class LasforceLaser @Inject constructor(connection: ConnectionService) : Laser {
    val LOG = LoggerFactory.getLogger(LasforceLaser::class.java)
    val connection = connection;

    override fun isConnected(): Boolean {
        return connection.isConnected()
    }

    override fun reConnect(): Boolean {
        return connection.reconnect()
    }

    fun getStatus(): String {
        //        sendMessage(InstructionLaserRequest("list_cache"))
        //        val socketResponse = getSocketResponse()
        //        return "${socketResponse.type}"
        return ""
    }

    override fun execute(request: LaserRequest): LaserResponse {
        if (request is ConnectRequest) {
            connection.reconnect();
            return ConnectResponse(connection.isConnected())
        }
        if (!connection.isConnected()) {
            connection.connect()
        }

        return connection.execute(request)
    }

    //    fun getSocketResponse() : LaserResponse {
    //        val dataInputString = DataInputStream(socket.getInputStream())
    //        val socketResponseJson = dataInputString.readLine();
    //        println("Response: " + socketResponseJson)
    //        return mapper.readValue(socketResponseJson, LaserResponse::class.java)
    //    }
    //
    //    override fun execute(request: LaserRequest?): LaserResponse {
    //        val message = mapper.writeValueAsString(request)
    //        val printStream = PrintStream(socket.getOutputStream())
    //        printStream.print(getLength(message))
    //        printStream.print(message);
    //        printStream.flush();
    //        return getSocketResponse()
    //    }
}