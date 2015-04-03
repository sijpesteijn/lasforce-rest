package nl.sijpesteijn.lasforce.services;

import nl.sijpesteijn.lasforce.rest.dto.Playlist;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public interface IPlaylistService {
    List<Playlist> getPlaylists();

    void play(Playlist playlist);

    void addToPlayList(Playlist playlist, AnimationMetaData animationMetaData);
}
