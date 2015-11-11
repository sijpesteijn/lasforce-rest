package nl.sijpesteijn.lasforce.laser.response

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * @author Gijs Sijpesteijn
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "response")
@JsonSubTypes(
//    @JsonSubTypes.Type(value = AnimationRequestResponse.class, name = "animation_response"),
//    @JsonSubTypes.Type(value = SendAnimationDataResponse.class, name = "send_animation_data_response"),
//    @JsonSubTypes.Type(value = InstructionResponse.class, name = "instruction_response"),
//    @JsonSubTypes.Type(value = OkResponse.class, name = "ok"),
    JsonSubTypes.Type(value = MessageResponse::class, name = "message"))
//    @JsonSubTypes.Type(value = ErrorResponse.class, name = "error")})
interface Response {
    var name:String
}

data class MessageResponse(val priority:Int, val data:String) :Response {
    override  var name="message"
}