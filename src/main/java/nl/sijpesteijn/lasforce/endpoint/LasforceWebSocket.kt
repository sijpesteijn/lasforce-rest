package nl.sijpesteijn.lasforce.endpoint

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sijpesteijn.lasforce.domain.Animation
import nl.sijpesteijn.lasforce.domain.Frame
import nl.sijpesteijn.lasforce.laser.LasforceLaser
import nl.sijpesteijn.lasforce.laser.laserRequests.CommandRequest
import nl.sijpesteijn.lasforce.laser.laserRequests.LaserRequest
import nl.sijpesteijn.lasforce.laser.laserResponses.ConnectResponse
import nl.sijpesteijn.lasforce.laser.laserResponses.ParseErrorResponse
import nl.sijpesteijn.lasforce.services.ConnectionService
import org.apache.commons.configuration.PropertiesConfiguration
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*
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
            if (request is CommandRequest && request.cmd == "play") {
                val animationInfo : Map<String, Object> = request.value as Map<String, Object>
                val animation: Animation = mapper.readValue(FileUtils.readFileToString(File("./tmp/" + animationInfo.get("animationId") + ".las")))
                animation.repeat = animationInfo.get("repeat") as Int
                animation.frame_time = animationInfo.get("frameTime") as Int
                request.value = animation
            }
            if (request is CommandRequest && request.cmd == "play_frame") {
                val animationInfo : Map<String, Object> = request.value as Map<String, Object>
                val animation: Animation = mapper.readValue(FileUtils.readFileToString(File("./tmp/" + animationInfo.get("animationId") + ".las")))
                val frames = ArrayList<Frame>()
                frames.add(animation.frames.get(animationInfo.get("frameId") as Int))
                animation.frames = frames
                animation.total_frames = 1
                animation.repeat = animationInfo.get("repeat") as Int
                animation.frame_time = animationInfo.get("frameTime") as Int
                request.value = animation
                request.cmd = "play"
            }

            val response = laser.execute(request);
            session.basicRemote.sendText(mapper.writeValueAsString(response))
        } catch(e: Exception) {
            println(e)
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
