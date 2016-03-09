package nl.sijpesteijn.lasforce.endpoint

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sijpesteijn.lasforce.laser.LasforceLaser
import nl.sijpesteijn.lasforce.laser.laserRequests.LaserRequest
import nl.sijpesteijn.lasforce.laser.laserResponses.ConnectResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.ParseErrorResponse
import nl.sijpesteijn.lasforce.services.ConnectionService
import org.apache.commons.configuration.PropertiesConfiguration
import javax.inject.Singleton
import javax.websocket.*
import javax.websocket.server.ServerEndpoint

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
@ServerEndpoint(value = "/ws/laser")
class LasforceWebSocket {
    val configuration = PropertiesConfiguration("lasforce.web.properties")
    val laser = LasforceLaser(ConnectionService(configuration))
    val mapper = jacksonObjectMapper()

    @OnOpen
    fun onOpen(session: Session) {
        println("OPEN")
        session.basicRemote.sendText(mapper.writeValueAsString(ConnectResponse(laser.isConnected())))
    }

    @OnMessage
    fun onMessage(txt: String, session: Session) {
        try {
            val request = mapper.readValue(txt, LaserRequest::class.java)
            println("${request.type} request received")
            val response = laser.execute(request);
            session.basicRemote.sendText(mapper.writeValueAsString(response))
        } catch(e: Exception) {
            session.basicRemote.sendText(mapper.writeValueAsString(ParseErrorResponse(error = e.message)))
        }
    }

    @OnClose
    fun onClose(reason: CloseReason, session: Session) {
        println("CLOSE ${reason}")
    }

    @OnError
    fun onError(session: Session, t: Throwable) {
        println("ERROR ${t}")
    }
}
