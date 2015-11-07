package nl.sijpesteijn.lasforce.guice

import com.google.inject.AbstractModule
import org.apache.commons.configuration.Configuration
import org.apache.commons.configuration.PropertiesConfiguration

/**
 * @author Gijs Sijpesteijn
 */
class ConfigurationModule:AbstractModule() {

    override fun configure() {
        try {
            bind(Configuration::class.java).toInstance(PropertiesConfiguration("lasforce.web.properties"))
        } catch(e:org.apache.commons.configuration.ConfigurationException) {
            throw IllegalArgumentException("lasforce.properties could not be found on the classpath.")
        }
    }

}