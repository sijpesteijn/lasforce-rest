package nl.sijpesteijn.lasforce.domain.laser.responses;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @author Gijs Sijpesteijn
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "response")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnimationRequestResponse.class, name = "animation_request"),
        @JsonSubTypes.Type(value = OkResponse.class, name = "ok"),
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error")})
public interface SocketResponse {
}
