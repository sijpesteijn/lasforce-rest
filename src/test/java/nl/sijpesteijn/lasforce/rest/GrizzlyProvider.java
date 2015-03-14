package nl.sijpesteijn.lasforce.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import nl.sijpesteijn.lasforce.guice.ConfigurationModule;
import nl.sijpesteijn.lasforce.guice.ServiceModule;
import nl.sijpesteijn.lasforce.guice.WebModule;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class GrizzlyProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrizzlyProvider.class);
    static final URI BASE_URI = getBaseURI();
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port( 9998 ).build();
    }
    public HttpServer httpServer;
    public WebResource service;
    public Injector injector;

    public GrizzlyProvider() {
        injector = Guice.createInjector(new WebModule(), new ConfigurationModule(), new ServiceModule());
        ResourceConfig rc = new PackagesResourceConfig( "nl.sijpesteijn.lasforce.rest" );
        rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory( rc, injector );
        try {
            httpServer =  GrizzlyServerFactory.createHttpServer(BASE_URI + "rest/", rc, ioc);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
        Client client = Client.create(new DefaultClientConfig());
        service = client.resource( getBaseURI() );
    }

}