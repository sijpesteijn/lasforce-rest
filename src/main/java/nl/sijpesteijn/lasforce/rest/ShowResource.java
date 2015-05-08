package nl.sijpesteijn.lasforce.rest;

import nl.sijpesteijn.lasforce.domain.Show;
import nl.sijpesteijn.lasforce.services.ShowService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
@Path("shows")
@Consumes(MediaType.APPLICATION_JSON)
public class ShowResource {
    @Inject
    private ShowService showService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Show> getShows() {
        return showService.getShows();
    }

    @GET
    @Path("/{id}")
    public Show getShow(@PathParam("id") int id) {
        return showService.getShow(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Show addShow(Show show) {
        if (show.getId() == 0) {
            return showService.addShow(show);
        } else {
            return showService.updateShow(show);
        }
    }
}
