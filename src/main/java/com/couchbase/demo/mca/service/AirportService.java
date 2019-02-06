package com.couchbase.demo.mca.service;

import com.couchbase.demo.mca.model.Result;

public interface AirportService {

	@SuppressWarnings("rawtypes")
	public Result findAirportById(String id);
	
	@SuppressWarnings("rawtypes")
	public Result findAirportByName(String name);

}
