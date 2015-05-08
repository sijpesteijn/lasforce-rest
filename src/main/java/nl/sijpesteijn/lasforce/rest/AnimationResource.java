package nl.sijpesteijn.lasforce.rest;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import nl.sijpesteijn.lasforce.domain.Animation;
import nl.sijpesteijn.lasforce.domain.LasForceAnimation;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.services.AnimationRunner;
import nl.sijpesteijn.lasforce.services.AnimationService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
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
    private AnimationService animationService;
    @Inject
    private AnimationRunner animationRunner;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Animation> getAnimations() throws LaserException {
        return animationService.getAnimations();
    }

    @GET
    @Path("/{id}")
    @Produces({RestBase.MEDIA_TYPE})
    public Animation loadAnimation(@PathParam("id") long id) throws LaserException {
        Animation animation = animationService.getAnimation(id);
        return animation;
    }

    @GET
    @Path("/{id}/data")
    @Produces({RestBase.MEDIA_TYPE})
    public LasForceAnimation loadLasForceAnimation(@PathParam("id") long id) throws LaserException {
        LasForceAnimation lasForceAnimation = animationService.getLasForceAnimation(id);
        return lasForceAnimation;
    }

//    @POST
//    @Path("/play")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response playAnimation(AnimationMetaData animationMetaData) {
//        animationRunner.playAnimation(animationMetaData);
//        return Response.ok().build();
//    }
//
//    @POST
//    @Path("/save/")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String saveAnimation(String json) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LaserIAnimation.class, new LaserAnimationDeserializer());
//        Gson gson = gsonBuilder.create();
//        LaserIAnimation laserAnimation = gson.fromJson(json, LaserIAnimation.class);
//        try {
//            animationService.update(laserAnimation);
//        } catch (LaserException e) {
//            return "FAIL";
//        }
//        return "OK";
//    }

    @POST
    @Path("/upload/")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public String uploadIlda(FormDataMultiPart formParams) throws LaserException {
        Map<String, List<FormDataBodyPart>> fieldsByName = formParams.getFields();
        // Usually each value in fieldsByName will be a list of length 1.
        // Assuming each field in the form is a file, just loop through them.

        for (List<FormDataBodyPart> fields : fieldsByName.values())
        {
            for (FormDataBodyPart field : fields)
            {
                InputStream is = field.getEntityAs(InputStream.class);
                try {
                    byte[] data = IOUtils.toByteArray(is);
                    animationService.uploadAnimation(field.getContentDisposition().getFileName(), data);
                } catch (IOException e) {
                    throw new LaserException(e);
                }
            }
        }
        return "OK";
    }

}
