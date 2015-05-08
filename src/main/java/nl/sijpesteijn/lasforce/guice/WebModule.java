package nl.sijpesteijn.lasforce.guice;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import nl.sijpesteijn.lasforce.rest.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Gijs Sijpesteijn
 */
public class WebModule extends ServletModule {
    private static final String JERSEY_API_JSON_POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";

    protected void configureServlets() {

//        bind(MessageWebSocketServlet.class).in(Singleton.class);
//        serve("/ws/message").with(MessageWebSocketServlet.class);
        bind(AnimationResource.class);
        bind(SequenceResource.class);
        bind(ShowResource.class);

        bind(PlaylistResource.class);
        bind(MessageResource.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");

        serve("/rest/*").with(GuiceContainer.class, params);
    }
}