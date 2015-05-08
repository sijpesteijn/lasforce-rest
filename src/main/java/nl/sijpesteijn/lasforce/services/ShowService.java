package nl.sijpesteijn.lasforce.services;

import com.google.inject.Provider;
import com.mongodb.*;
import nl.sijpesteijn.lasforce.domain.Sequence;
import nl.sijpesteijn.lasforce.domain.Show;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class ShowService {
    private DBCollection showsCollection;

    @Inject
    public ShowService(Provider<MongoClient> mongoProvider) {
        DB db = mongoProvider.get().getDB("lasforce");
        this.showsCollection = db.getCollection("shows");
    }

    public List<Show> getShows() {
        DBCursor cursor = showsCollection.find();
        List<Show> shows = new ArrayList<Show>();
        while(cursor.hasNext()) {
            BasicDBObject next = (BasicDBObject) cursor.next();
            Show show = new Show();
            show.setId(next.getObjectId("_id").getTimestamp());
            show.setName(next.getString("name"));
            shows.add(show);
        }
        return shows;
    }

    public Show addShow(Show show) {
        BasicDBObject dbObject = new BasicDBObject("name",show.getName());
        showsCollection.insert(dbObject);
        show.setId(dbObject.getObjectId("_id").getTimestamp());
        return show;
    }

    public Show getShow(int id) {
        BasicDBObject dbObject = new BasicDBObject("_id", id);
        DBObject one = showsCollection.findOne(dbObject);
        Show show = new Show();
        show.setId(dbObject.getInt("_id"));
        show.setName(dbObject.getString("name"));
        show.setSequences(mapSequences(one));
        return show;
    }

    private List<Sequence> mapSequences(DBObject dbObject) {
        BasicDBList dbSequences = (BasicDBList) dbObject.get("sequences");
        List<Sequence> sequences = new ArrayList<Sequence>();
        for(int i = 0; i < dbSequences.size(); i++) {
            BasicDBObject dbSequence = (BasicDBObject) dbSequences.get(i);
            Sequence sequence = new Sequence();
            sequence.setId(dbSequence.getInt("_id"));
            sequence.setName(dbSequence.getString("name"));
            sequences.add(sequence);
        }
        return sequences;
    }

    public Show updateShow(Show show) {
        return null;
    }
}
