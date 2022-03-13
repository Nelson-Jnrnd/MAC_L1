package ch.heig.mac;

import java.util.List;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;


public class Requests {
    private final Cluster cluster;

    public Requests(Cluster cluster) {
        this.cluster = cluster;
    }

    public List<String> getCollectionNames() {
        QueryResult result = cluster.query(
                "SELECT RAW r.name\n" +
                        "FROM system:keyspaces r\n" +
                        "WHERE r.`bucket` = \"mflix-sample\";"
        );
        return result.rowsAs(String.class);
    }

    public List<JsonObject> inconsistentRating() {
        QueryResult result = cluster.query(
                "SELECT imdb.id as imdb_id, tomatoes.viewer.rating as tomatoes_rating, imdb.rating as imdb_rating\n" +
                        "FROM `mflix-sample`._default.`movies`\n" +
                        "WHERE tomatoes.viewer.rating  <> 0\n" +
                        "AND ABS(imdb.rating - tomatoes.viewer.rating) > 7;"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> hiddenGem() {
        QueryResult result = cluster.query(
                "SELECT title \n" +
                        "FROM `mflix-sample`._default.`movies`\n" +
                        "WHERE tomatoes.critic.meter = 100\n" +
                        "AND tomatoes.viewer.rating IS NOT VALUED"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> topReviewers() {
         QueryResult result = cluster.query(
                "SELECT name, count(*) as cnt\n" +
                        "FROM `mflix-sample`._default.`comments`\n" +
                        "GROUP BY name\n" +
                        "ORDER BY cnt DESC\n" +
                        "LIMIT 10"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<String> greatReviewers() {
        QueryResult result = cluster.query(
                "SELECT RAW name\n" +
                        "FROM `mflix-sample`._default.`comments`\n" +
                        "GROUP BY name\n" +
                        "HAVING count(*) > 300"
        );
        return result.rowsAs(String.class);
    }

    public List<JsonObject> bestMoviesOfActor(String actor) {
        QueryResult result = cluster.query(
                "SELECT imdb.id as imdb_id, imdb.rating as rating, `cast`\n" +
                        "FROM `mflix-sample`._default.`movies`\n" +
                        "WHERE imdb.rating > 8\n" +
                        "AND \"" + actor + "\" IN `cast`"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> plentifulDirectors() {
        QueryResult result = cluster.query(
                "SELECT directors AS director_name, COUNT(directors) AS count_film\n" +
                        "FROM `mflix-sample`.`_default`.`movies`\n" +
                        "UNNEST directors\n" +
                        "GROUP BY directors\n" +
                        "HAVING COUNT(directors) > 30\n" +
                        "ORDER BY count_film DESC"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> confusingMovies() {
        QueryResult result = cluster.query(
                "SELECT _id as movie_id, title\n" +
                        "FROM `mflix-sample`._default.`movies`\n" +
                        "WHERE ARRAY_COUNT(directors) > 20"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> commentsOfDirector1(String director) {
        QueryResult result = cluster.query(
                "SELECT comments.text, comments.movie_id AS movie_id\n" +
                        "FROM `mflix-sample`.`_default`.`movies`\n" +
                        "INNER JOIN `mflix-sample`.`_default`.`comments` ON comments.movie_id = movies._id\n" +
                        "WHERE \"" + director + "\" IN movies.directors"
        );
        return result.rowsAs(JsonObject.class);
    }

    public List<JsonObject> commentsOfDirector2(String director) {
        QueryResult result = cluster.query(
                "SELECT text, comments.movie_id AS movie_id\n" +
                        "FROM `mflix-sample`.`_default`.`comments`\n" +
                        "WHERE comments.movie_id IN (\n" +
                        "    SELECT RAW _id\n" +
                        "    FROM `mflix-sample`.`_default`.`movies`\n" +
                        "    WHERE \"" + director + "\" IN directors)"
        );
        return result.rowsAs(JsonObject.class);
    }

    // Returns the number of documents updated.
    public long removeEarlyProjection(String movieId) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    public List<JsonObject> nightMovies() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }


}
