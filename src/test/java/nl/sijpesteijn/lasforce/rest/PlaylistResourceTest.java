package nl.sijpesteijn.lasforce.rest;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

@Ignore
public class PlaylistResourceTest {
    private GrizzlyProvider grizzly = new GrizzlyProvider();

    @Test
    public void testGetPlaylists() throws Exception {
        WebResource service = grizzly.service;

        ClientResponse resp = service.path("rest").path("animations").path("list")
                .accept(MediaType.APPLICATION_JSON)
                .get( ClientResponse.class );

        String playlists = resp.getEntity( String.class );

        assertEquals( 200, resp.getStatus() );
        assertEquals("[{\"name\":\"01-01-2000\",\"lastUpdate\":\"dog\"}]", playlists);
    }

    @Test
    public void testPlaySequence() throws Exception {

    }

    @Test
    public void testAddToPlaylist() throws Exception {

    }
}