package com.couchbase.demo.mca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.demo.mca.model.IValue;
import com.couchbase.demo.mca.model.Result;
import com.couchbase.demo.mca.model.Error;
import com.couchbase.demo.mca.service.AirportService;

@RestController
@RequestMapping("/api/airports")
public class AirportControllerImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirportControllerImpl.class);

    private final AirportService airportService;

    @Autowired
    public AirportControllerImpl(AirportService airportService) {
        this.airportService = airportService;
    }
    
    @RequestMapping(value="/airport/{id}/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("rawtypes")
    public ResponseEntity<? extends IValue> findAirportById(@PathVariable("id") String id) {

	    LOGGER.debug("AirportControllerImpl - findAirportById - find airport with Id : " + id);
    	
        try {
        	Result result = airportService.findAirportById(id);
        	LOGGER.debug("AirportControllerImpl - findAirportById - find airport with Id  result : " + result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }      
    
    @RequestMapping(value="/airport/name/{name}/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends IValue> findAirportByState(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(airportService.findAirportByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }  
    
}
