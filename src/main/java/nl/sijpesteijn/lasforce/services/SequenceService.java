package nl.sijpesteijn.lasforce.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.*;
import nl.sijpesteijn.lasforce.domain.Sequence;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class SequenceService {
    private DBCollection sequencesCollection;

    @Inject
    public SequenceService(Provider<MongoClient> mongoProvider) {
        DB db = mongoProvider.get().getDB("lasforce");
        this.sequencesCollection = db.getCollection("sequences");
    }

    public List<Sequence> getSequences() {
        DBCursor cursor = sequencesCollection.find();
        List<Sequence> sequences = new ArrayList<Sequence>();
        while(cursor.hasNext()) {
            BasicDBObject next = (BasicDBObject) cursor.next();
            Sequence sequence = new Sequence();
            sequence.setId(next.getObjectId("_id").getTimestamp());
            sequence.setName(next.getString("name"));
            sequences.add(sequence);
        }
        return sequences;
    }

    public Sequence addSequence(Sequence sequence) {
        BasicDBObject dbObject = new BasicDBObject("name",sequence.getName());
        sequencesCollection.insert(dbObject);
        sequence.setId(dbObject.getObjectId("_id").getTimestamp());
        return sequence;
    }

    public Sequence getSequence(int id) {
        BasicDBObject dbObject = new BasicDBObject("_id", id);
        Sequence sequence = new Sequence();
        sequence.setId(dbObject.getInt("_id"));
        sequence.setName(dbObject.getString("name"));
        return sequence;
    }

    public Sequence updateSequence(Sequence sequence) {
        return sequence;
    }
}
