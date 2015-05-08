package nl.sijpesteijn.lasforce.rest;

import nl.sijpesteijn.lasforce.domain.Sequence;
import nl.sijpesteijn.lasforce.services.SequenceService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
@Path("sequences")
@Consumes(MediaType.APPLICATION_JSON)
public class SequenceResource {

    @Inject
    private SequenceService sequenceService;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sequence> getSequences() {
        return sequenceService.getSequences();
    }

    @GET
    @Path("/{id}")
    public Sequence getSequence(@PathParam("id") int id) {
        return sequenceService.getSequence(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Sequence addSequence(Sequence sequence) {
        if (sequence.getId() == 0) {
            return sequenceService.addSequence(sequence);
        } else {
            return sequenceService.updateSequence(sequence);
        }
    }}
