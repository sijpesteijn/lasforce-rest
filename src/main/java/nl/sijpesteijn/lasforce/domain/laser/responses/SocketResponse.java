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
        @JsonSubTypes.Type(value = SendAnimationDataResponse.class, name = "animation_data"),
        @JsonSubTypes.Type(value = ListResponse.class, name = "list"),
        @JsonSubTypes.Type(value = OkResponse.class, name = "ok") })
public interface SocketResponse {
}
