package com.couchbase.demo.mca.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = "couchbase")
@Data
@Getter
@Setter
public class CouchbaseProperties {

    private String bucketName;

    private String bucketPassword;

    private String userName;

    private String userPassword;

    private List<ClusterEntry> clusterEntries = new ArrayList<>();

    private int activeEntries;

    @Getter
    @Setter
    @ToString
    public static class ClusterEntry {

        private String identifier;
        private List<String> hostnames;
        private int priority;
    }
}
