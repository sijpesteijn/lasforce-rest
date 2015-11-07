package nl.sijpesteijn.lasforce.guice

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener

/**
 * @author Gijs Sijpesteijn
 */
class GuiceServletConfig: GuiceServletContextListener() {

    override fun getInjector(): Injector? {
        return Guice.createInjector(ConfigurationModule(), WebModule());
    }
}