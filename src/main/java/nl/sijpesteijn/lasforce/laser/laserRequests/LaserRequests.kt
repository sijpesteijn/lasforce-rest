package nl.sijpesteijn.lasforce.laser.laserRequests

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import nl.sijpesteijn.lasforce.domain.Frame

/**
 * @author Gijs Sijpesteijn
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(
        JsonSubTypes.Type(value = PlayFrameRequest::class, name = "play_frame"),
        JsonSubTypes.Type(value = ConnectRequest::class, name = "connect_laser"),
        JsonSubTypes.Type(value = CommandRequest::class, name = "laser_cmd"),
        JsonSubTypes.Type(value = StopRequest::class, name = "laser_stop"),
        JsonSubTypes.Type(value = DisconnectRequest::class, name = "disconnect_laser"))
interface LaserRequest {
    val type: String
}

data class PlayFrameRequest(val frame: Frame) : LaserRequest {
    override val type = "play_frame"
}

class ConnectRequest : LaserRequest {
    override val type = "connect_laser"
}

data class DisconnectRequest(override val type: String = "disconnect_laser") : LaserRequest

data class StopRequest(override val type: String = "laser_stop") : LaserRequest

data class CommandRequest(val command : String, val value : String?) : LaserRequest {
    override val type = "laser_cmd"
}

data class InstructionLaserRequest(val key: String, var value: Any? = "") : LaserRequest {
    override val type = "instruction_request"
}

data class FrameId(val frameId: Int)