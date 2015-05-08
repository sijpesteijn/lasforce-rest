package nl.sijpesteijn.lasforce.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.*;
import nl.sijpesteijn.lasforce.domain.Sequence;
import nl.sijpesteijn.lasforce.rest.dto.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class PlaylistService {
    @Inject
    private Provider<MongoClient> mongoProvider;
    @Inject
    private SequenceService sequenceService;

    public List<Playlist> getPlaylists() {
        List<Sequence> sequences = sequenceService.getSequences();
        DB db = mongoProvider.get().getDB("lasforce");
        List<Playlist> playlists = new ArrayList();
//        DBCollection collection = db.getCollection("playlists");
//        DBCursor cursor = collection.find();
//        while(cursor.hasNext()) {
//            DBObject next = cursor.next();
//            Playlist playlist = new Playlist((String) next.get("name"), sequences);
//            playlists.add(playlist);
//        }
        return playlists;
    }

    public void play(Playlist playlist) {

    }

    public void addToPlayList(Playlist playlist, AnimationMetaData animationMetaData) {

    }
}
