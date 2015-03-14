package nl.sijpesteijn.lasforce.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.*;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.rest.dto.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class PlaylistService implements IPlaylistService {
    @Inject
    private Provider<MongoClient> mongoProvider;
    @Inject
    private ISequenceService sequenceService;

    @Override
    public List<Playlist> getPlaylists() {
        List<SequenceInfo> sequences = sequenceService.getSequences();
        DB db = mongoProvider.get().getDB("lasforce");
        List<Playlist> playlists = new ArrayList();
        DBCollection collection = db.getCollection("playlists");
        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            DBObject next = cursor.next();
            Playlist playlist = new Playlist((String) next.get("name"), sequences);
            playlists.add(playlist);
        }
        return playlists;
    }

    @Override
    public void play(Playlist playlist) {

    }

    @Override
    public void addToPlayList(Playlist playlist, AnimationInfo animationInfo) {

    }
}
