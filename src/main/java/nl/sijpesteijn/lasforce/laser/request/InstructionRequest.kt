package nl.sijpesteijn.lasforce.laser.request

/**
 * @author Gijs Sijpesteijn
 */
interface Request {}

data class InstructionRequest(val key:String, var value:Any? = "") : Request {
    val command:String = "instruction_request"
}