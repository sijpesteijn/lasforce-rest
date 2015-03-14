package nl.sijpesteijn.lasforce.rest;

import nl.sijpesteijn.lasforce.rest.dto.Playlist;
import nl.sijpesteijn.lasforce.services.AnimationInfo;
import nl.sijpesteijn.lasforce.services.IPlaylistService;

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
@Path("playlists")
@Consumes({MediaType.APPLICATION_JSON})
public class PlaylistResource {

    @Inject
    private IPlaylistService playlistService;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Playlist> getPlaylists() {
        return playlistService.getPlaylists();
    }

    @POST
    @Path("/{playlist}/play")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playPlaylist(@PathParam("playlist") Playlist playlist) {
        playlistService.play(playlist);
        return Response.ok().build();
    }

    @POST
    @Path("/{playlist}/add/{animation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToPlaylist(@PathParam("playlist") Playlist playlist, @PathParam("animation")AnimationInfo animationInfo) {
        playlistService.addToPlayList(playlist, animationInfo);
        return Response.ok().build();
    }
}
