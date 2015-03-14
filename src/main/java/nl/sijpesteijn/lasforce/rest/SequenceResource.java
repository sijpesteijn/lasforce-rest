package nl.sijpesteijn.lasforce.rest;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.services.ISequenceService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
@Path("sequence")
@Produces(RestBase.MEDIA_TYPE)
public class SequenceResource {

    @Inject
    private ISequenceService sequenceService;

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public List<SequenceInfo> getSequences() {
        return sequenceService.getSequences();
    }

    @POST
    @Path("/play/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response playSequence(SequenceInfo sequenceInfo) {
        sequenceService.play(sequenceInfo);
        return Response.ok().build();
    }

}
