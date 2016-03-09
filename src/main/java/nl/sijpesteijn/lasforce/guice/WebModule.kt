package nl.sijpesteijn.lasforce.guice

import com.google.inject.servlet.ServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import nl.sijpesteijn.lasforce.endpoint.LasforceWebSocket
import nl.sijpesteijn.lasforce.endpoint.SettingsEndpoint
import nl.sijpesteijn.lasforce.laser.Laser
import nl.sijpesteijn.lasforce.laser.LaserMock
import nl.sijpesteijn.lasforce.laser.LasforceLaser
import nl.sijpesteijn.lasforce.rest.AnimationEndpoint
import nl.sijpesteijn.lasforce.services.ConnectionService
import org.codehaus.jackson.jaxrs.JacksonJsonProvider
import java.util.*
import javax.inject.Singleton

/**
 * @author Gijs Sijpesteijn
 */
const val JERSEY_API_JSON_POJO_MAPPING_FEATURE : String = "com.sun.jersey.api.json.POJOMappingFeature"

class WebModule: ServletModule() {

    override fun configureServlets() {
        bind(AnimationEndpoint::class.java)
        bind(SettingsEndpoint::class.java)

        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            bind(Laser::class.java).to(LaserMock::class.java)
        } else {
            bind(Laser::class.java).to(LasforceLaser::class.java)
        }

        bind(ConnectionService::class.java)
        bind(LasforceWebSocket::class.java)

        val params = HashMap<String, String>();
        params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");
        bind(JacksonJsonProvider::class.java).`in`(Singleton::class.java)
        serve("/rest/*").with(GuiceContainer::class.java, params)
    }
}