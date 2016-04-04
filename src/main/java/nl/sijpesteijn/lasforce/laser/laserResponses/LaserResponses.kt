package nl.sijpesteijn.lasforce.laser.laserResponses

/**
 * @author Gijs Sijpesteijn
 */
interface LaserResponse {
    val type: String
}

data class OkResponse(override val type : String = "ok_response") : LaserResponse
data class InfoResponse(override val type : String = "info_response", val info : Any) : LaserResponse
data class ConnectResponse(val status: Boolean,override val type : String = "connect_laser") : LaserResponse
data class DisconnectResponse(val status: Boolean,override val type : String = "disconnect_laser") : LaserResponse
data class NotConnectedResponse(override val type : String = "notconnected_laser") : LaserResponse
data class ParseErrorResponse(override val type : String = "parse_error", val error: String?) : LaserResponse
