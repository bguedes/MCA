package com.couchbase.demo.mca.service;

import com.couchbase.demo.mca.model.Result;

public interface TopologyService {

	@SuppressWarnings("rawtypes")
	public Result enableTopology(String topologyIdentifier);
	
	@SuppressWarnings("rawtypes")
	public Result disableTopology(String topologyIdentifier);
	
	@SuppressWarnings("rawtypes")
	public Result failTopology(String topologyIdentifier);
}
