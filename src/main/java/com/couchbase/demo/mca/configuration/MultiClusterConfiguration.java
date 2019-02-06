package com.couchbase.demo.mca.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.mc.ClusterSpec;
import com.couchbase.client.mc.MultiClusterBucket;
import com.couchbase.client.mc.MultiClusterClient;
import com.couchbase.client.mc.coordination.Coordinator;
import com.couchbase.client.mc.coordination.Coordinators;
import com.couchbase.client.mc.coordination.IsolatedCoordinator;
import com.couchbase.client.mc.coordination.IsolatedCoordinator.Options;
import com.couchbase.client.mc.coordination.SimpleTopologyAdmin;
import com.couchbase.client.mc.coordination.TopologyBehavior;
import com.couchbase.client.mc.detection.FailureDetectorFactory;
import com.couchbase.client.mc.detection.TrafficMonitoringFailureDetector;
import com.couchbase.client.mc.detection.TrafficMonitoringFailureDetectorFactory;

@Configuration
@EnableConfigurationProperties(CouchbaseProperties.class)
public class MultiClusterConfiguration {

    private final CouchbaseProperties props;
    
    private static final Set<ServiceType> serviceTypes = new HashSet<>();
    
    @Autowired
    public MultiClusterConfiguration(final CouchbaseProperties couchbaseProperties) {
        this.props = couchbaseProperties;
    }
    
    @Bean
    public Coordinator coordinator() {
    	
        serviceTypes.add(ServiceType.BINARY);
        serviceTypes.add(ServiceType.QUERY);
    	
        List<ClusterSpec> specs = new ArrayList<ClusterSpec>();
        for (CouchbaseProperties.ClusterEntry entry : props.getClusterEntries()) {
            specs.add(ClusterSpec.create(
                new HashSet<>(entry.getHostnames()),
                entry.getIdentifier(),
                entry.getPriority()
            ));
        }

        Options options = new IsolatedCoordinator.Options()
            .activeEntries(props.getActiveEntries())
            //.failoverNumNodes(1)
            .topologyBehavior(TopologyBehavior.WRAP_AT_END)
            .serviceTypes(serviceTypes)
            .gracePeriod(1000)
            .clusterSpecs(specs);
        return Coordinators.isolated(options);
    }

    @Bean
    public FailureDetectorFactory<?> failureDetectorFactory() {
        TrafficMonitoringFailureDetector.Options options = TrafficMonitoringFailureDetector
            .options()
            //.nodeScope(true)
            //.maxFailedOperations(2)
            .failureInterval(60);
        return new TrafficMonitoringFailureDetectorFactory(coordinator(), options);
    }

    @Bean
    public MultiClusterClient multiClusterClient() {
       MultiClusterClient client = new MultiClusterClient(coordinator(), failureDetectorFactory());
       if (props.getUserName() != null && !props.getUserName().isEmpty()) {
           client.authenticate(
        		   props.getUserName(), 
        		   props.getUserPassword());
       }
       return client;
    }

    @Bean
    public MultiClusterBucket multiClusterBucket() {
        if (props.getUserName() == null || props.getUserName().isEmpty()) {
            return multiClusterClient().openBucket(
            		props.getBucketName(), 
            		props.getBucketPassword());
        } else {
            return multiClusterClient().openBucket(
            		props.getBucketName(), 
            		null);
        }
    }

    @Bean
    public SimpleTopologyAdmin topologyAdmin() {
        return SimpleTopologyAdmin.create(coordinator());
    }    
}
