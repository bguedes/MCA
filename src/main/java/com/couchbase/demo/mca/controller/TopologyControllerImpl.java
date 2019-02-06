package com.couchbase.demo.mca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.demo.mca.model.Error;
import com.couchbase.demo.mca.model.IValue;
import com.couchbase.demo.mca.service.TopologyService;

@RestController
@RequestMapping("/api/topology")
public class TopologyControllerImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyControllerImpl.class);
	
    private final TopologyService topologyService;

    @Autowired
    public TopologyControllerImpl(TopologyService topologyService) {
        this.topologyService = topologyService;
    }
	
    @RequestMapping(value="/fail/{identifier}/", method=RequestMethod.POST)
    public ResponseEntity<? extends IValue> failTopologyByIdentifier(@PathVariable("identifier") String topologyIdentifier) {
        try {
        	topologyService.failTopology(topologyIdentifier);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    } 
    
    @RequestMapping(value="/enable/{identifier}/", method=RequestMethod.POST)
    public ResponseEntity<? extends IValue> enableTopologyByIdentifier(@PathVariable("identifier") String topologyIdentifier) {
        try {
        	topologyService.enableTopology(topologyIdentifier);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }      
    
    @RequestMapping(value="/disable/{identifier}/", method=RequestMethod.POST)
    public ResponseEntity<? extends IValue> diableTopologyByIdentifier(@PathVariable("identifier") String topologyIdentifier) {
    	
    	LOGGER.info("TopologyControllerImpl - diableTopologyByIdentifier with identifier " + topologyIdentifier);
    	
        try {
        	topologyService.disableTopology(topologyIdentifier);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }
}
