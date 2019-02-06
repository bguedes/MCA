package com.couchbase.demo.mca.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.mc.MultiClusterBucket;
import com.couchbase.client.mc.coordination.SimpleTopologyAdmin;
import com.couchbase.demo.mca.model.Result;

@Service
public class TopologyServiceImpl implements TopologyService {

    //private final MultiClusterBucket bucket;
    private final SimpleTopologyAdmin topologyAdmin;

	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyServiceImpl.class);
    
    @Autowired
	public TopologyServiceImpl(final MultiClusterBucket bucket, 
			final SimpleTopologyAdmin topologyAdmin) {
		super();
		//this.bucket = bucket;
		this.topologyAdmin = topologyAdmin;
	}    
    
	@Override
	@SuppressWarnings("rawtypes")
	public Result enableTopology(String topologyIdentifier) {
		topologyAdmin.activate(topologyIdentifier);
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Result disableTopology(String topologyIdentifier) {
		topologyAdmin.deactivate(topologyIdentifier);
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Result failTopology(String topologyIdentifier) {
		topologyAdmin.fail(topologyIdentifier);
		return null;
	}
}
