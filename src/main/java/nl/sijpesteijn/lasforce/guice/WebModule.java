package nl.sijpesteijn.lasforce.guice;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import nl.sijpesteijn.lasforce.rest.AnimationResource;
import nl.sijpesteijn.lasforce.rest.PlaylistResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Gijs Sijpesteijn
 */
public class WebModule extends ServletModule {
    private static final String JERSEY_API_JSON_POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";

    protected void configureServlets() {
//        ResourceConfig rc = new PackagesResourceConfig( "nl.sijpesteijn.ilda.rest" );
//        for ( Class<?> resource : rc.getClasses() ) {
//            bind( resource );
//        }

        bind(AnimationResource.class);
        bind(PlaylistResource.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");

        serve("/rest/*").with(GuiceContainer.class, params);
    }
}