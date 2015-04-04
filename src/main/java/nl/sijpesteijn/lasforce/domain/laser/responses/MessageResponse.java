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
        @JsonSubTypes.Type(value = InfoMessageResponse.class, name = "message")})
public interface MessageResponse {
}
