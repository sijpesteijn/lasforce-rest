package nl.sijpesteijn.lasforce.endpoint

import nl.sijpesteijn.lasforce.exception.LaserException
import org.apache.commons.configuration.Configuration
import org.apache.commons.configuration.ConfigurationConverter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
@Path("settings")
@Consumes(MediaType.APPLICATION_JSON)
class SettingsEndpoint {
    val configuration:Configuration

    @Inject
    constructor(configuration: Configuration) {
        this.configuration = configuration
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun get():Properties {
        return ConfigurationConverter.getProperties(configuration)
    }

}