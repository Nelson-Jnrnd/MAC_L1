package ch.heig.mac;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.couchbase.client.core.error.IndexExistsException;
import com.couchbase.client.java.Cluster;

public class Indices {
    private final Cluster cluster;

    protected Map<String, List<String>> requiredIndices = Map.ofEntries(
            // Map.entry(<method name>, List.of("CREATE INDEX ...", "CREATE INDEX ..."))
            Map.entry("commentsOfDirector1", List.of("CREATE INDEX def_movies_id ON `default`:`mflix-sample`.`_default`.`movies`(`_id`)",
                    "CREATE INDEX adv_movie_id ON `default`:`mflix-sample`.`_default`.`comments`(`movie_id`)")),
            Map.entry("nightMovies", List.of("CREATE INDEX def_movies_id ON `default`:`mflix-sample`.`_default`.`movies`(`_id`)"))
    );

    public Indices(Cluster cluster) {
        this.cluster = cluster;
    }

    private void createIndex(String createQuery) {
        try {
            cluster.query(createQuery);
        } catch (IndexExistsException ex) {
            // Ignore already existing index
            // You may need to manually delete old indices if you change them in this class after executing this method
        }
    }

    public void createRequiredIndicesOf(String questionMethodName) {
        requiredIndices.getOrDefault(questionMethodName, List.of()).forEach(this::createIndex);
    }

    public void createRequiredIndices() {
        requiredIndices.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(this::createIndex);
    }

    public Set<String> getMethodNamesOfRequiredIndices() {
        return requiredIndices.keySet();
    }
}
