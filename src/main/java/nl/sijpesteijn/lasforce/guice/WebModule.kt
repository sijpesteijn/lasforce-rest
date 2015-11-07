package nl.sijpesteijn.lasforce.guice

import com.google.inject.servlet.ServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import nl.sijpesteijn.lasforce.endpoint.SettingsEndpoint
import nl.sijpesteijn.lasforce.rest.AnimationEndpoint
import org.codehaus.jackson.jaxrs.JacksonJsonProvider
import java.util.*
import javax.inject.Singleton

/**
 * @author Gijs Sijpesteijn
 */
class WebModule: ServletModule() {

    companion object {
        private val JERSEY_API_JSON_POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature"
    }

    override fun configureServlets() {
        bind(AnimationEndpoint::class.java)
        bind(SettingsEndpoint::class.java)
        val params = HashMap<String, String>();
        params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");
        bind(JacksonJsonProvider::class.java).`in`(Singleton::class.java)
        serve("/rest/*").with(GuiceContainer::class.java, params);
    }
}