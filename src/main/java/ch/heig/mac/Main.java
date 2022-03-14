package ch.heig.mac;

import com.couchbase.client.java.Cluster;

public class Main {

    public static final boolean hugoCredentials = true;

    public static Cluster openConnection() {
        var connectionString = "127.0.0.1";
        var username = hugoCredentials ? "password" : "Administrator";
        var password = hugoCredentials ? "password" : "Super2012";

        Cluster cluster = Cluster.connect(
                connectionString,
                username,
                password
        );
        return cluster;
    }

    public static void main(String[] args) {
        var cluster = openConnection();

        var requests = new Requests(cluster);
        var indices = new Indices(cluster);

        indices.createRequiredIndices();

        requests.getCollectionNames().forEach(System.out::println);
        requests.inconsistentRating().forEach(System.out::println);
    }
}
