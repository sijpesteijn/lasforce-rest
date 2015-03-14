package nl.sijpesteijn.lasforce.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import java.util.HashMap;
import java.util.Map;

/** Guice servlet config. */
public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(new ConfigurationModule(), new WebModule(), new ServiceModule());
        return injector;
    }
}