package nl.sijpesteijn.lasforce.rest;

import com.sun.jersey.api.client.ClientResponse;
import nl.sijpesteijn.lasforce.services.AnimationInfo;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class AnimationResourceTest {
    private GrizzlyProvider grizzly = new GrizzlyProvider();

    @Test
    public void testGetAnimations() throws Exception {

    }

    @Test
    public void testLoadAnimation() throws Exception {

    }

    @Test
    public void testSaveAnimation() throws Exception {

    }

    @Test
    public void testUploadIlda() throws Exception {

    }

    @Test
    public void testPlayAnimation() throws Exception {
        AnimationInfo animation = new AnimationInfo();
        animation.setName("PeaceDove8");
        animation.setLastUpdate("2015-03-13T13:54:33.567Z");
        animation.setFrameRate(24);

        ClientResponse response = grizzly.service.path("rest")
                .path("animations")
                .path("play")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
//                .header("animation_info", animation)
                .post(ClientResponse.class,animation);
        assertEquals(response.getStatus(), 200);

    }
}