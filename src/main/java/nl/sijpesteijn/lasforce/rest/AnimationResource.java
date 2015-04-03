package nl.sijpesteijn.lasforce.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaReader;
import nl.sijpesteijn.lasforce.domain.LaserAnimation;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.laser.LaserFormatConverter;
import nl.sijpesteijn.lasforce.services.AnimationMetaData;
import nl.sijpesteijn.lasforce.services.IAnimationRunner;
import nl.sijpesteijn.lasforce.services.IAnimationService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
@Path("animations")
@Consumes(MediaType.APPLICATION_JSON)
public class AnimationResource {

    @Inject
    private IAnimationService animationService;
    @Inject
    private IAnimationRunner animationRunner;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AnimationMetaData> getAnimations() {
        return animationService.getAnimations();
    }

    @GET
    @Path("/load/{name}")
    @Produces({RestBase.MEDIA_TYPE})
    public String loadAnimation(@PathParam("name") String name) throws IOException, URISyntaxException {
        LaserAnimation animation = (LaserAnimation) animationService.load(name);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LaserAnimation.class, new LaserAnimationSerializer());
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(animation);
        return json;
    }

    @POST
    @Path("/play")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response playAnimation(AnimationMetaData animationMetaData) {
        animationRunner.playAnimation(animationMetaData);
        return Response.ok().build();
    }

    @POST
    @Path("/save/")
    @Consumes({MediaType.APPLICATION_JSON})
    public String saveAnimation(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LaserAnimation.class, new LaserAnimationDeserializer());
        Gson gson = gsonBuilder.create();
        LaserAnimation laserAnimation = gson.fromJson(json, LaserAnimation.class);
        try {
            animationService.save(laserAnimation);
        } catch (LaserException e) {
            return "FAIL";
        }
        return "OK";
    }

    @POST
    @Path("/upload/")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public String uploadIlda(FormDataMultiPart formParams) throws LaserException {
        Map<String, List<FormDataBodyPart>> fieldsByName = formParams.getFields();
        LaserFormatConverter converter = new LaserFormatConverter();
        // Usually each value in fieldsByName will be a list of length 1.
        // Assuming each field in the form is a file, just loop through them.

        for (List<FormDataBodyPart> fields : fieldsByName.values())
        {
            for (FormDataBodyPart field : fields)
            {
                InputStream is = field.getEntityAs(InputStream.class);
                try {
                    byte[] data = IOUtils.toByteArray(is);
                    IldaReader ildaReader = new IldaReader();
                    IldaFormat ildaFormat = ildaReader.read(data);
                    LaserAnimation animation = (LaserAnimation) converter.convertIlda(field.getContentDisposition().getFileName(),
                            ildaFormat);
                    animationService.save(animation);
                } catch (IOException e) {
                    throw new LaserException(e);
                } catch (URISyntaxException e) {
                    throw new LaserException(e);
                }
            }
        }
        return "OK";
    }

}
