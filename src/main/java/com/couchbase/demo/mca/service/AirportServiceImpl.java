package com.couchbase.demo.mca.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static com.couchbase.client.java.query.dsl.Expression.s;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.mc.MultiClusterBucket;
import com.couchbase.client.mc.coordination.SimpleTopologyAdmin;
import com.couchbase.demo.mca.model.Result;

@Service
public class AirportServiceImpl implements AirportService {

    private final MultiClusterBucket bucket;
    //private final SimpleTopologyAdmin topologyAdmin;

	private static final Logger LOGGER = LoggerFactory.getLogger(AirportServiceImpl.class);
    
    @Autowired
	public AirportServiceImpl(final MultiClusterBucket bucket, 
			final SimpleTopologyAdmin topologyAdmin) {
		super();
		this.bucket = bucket;
		//this.topologyAdmin = topologyAdmin;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Result findAirportById(String id) {
		
		//LOGGER.info("AirportServiceImpl - findAirportById");
		
		JsonDocument doc = bucket.get(id, 1, TimeUnit.SECONDS);

		//LOGGER.info("AirportServiceImpl - findAirportById - get done: " + doc.toString());
		
//        JsonObject responseContent;
//		if(doc == null) {
//			responseContent = JsonObject.create().put("success", false).put("failure", "No Airport document for id :" + id);
//		} else {
//			responseContent = JsonObject.create().put("success", true).put("data", doc.content());
//		}

		//LOGGER.info("AirportServiceImpl - findAirportById - get done: " + doc.toString());
		
		return Result.of(new ResponseEntity<String>(doc.content().toString(), HttpStatus.OK));
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Result findAirportByName(String name) {
		
		Statement selectAirportByName = select("*").from(i("travel-sample")).where(x("airportname").eq(s(name)));
		
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(selectAirportByName), 5, TimeUnit.SECONDS);

		LOGGER.info("Executing Query: {}", selectAirportByName.toString());

		List<Map<String, Object>> rows = new ArrayList<>();		
        for (N1qlQueryRow row : result.allRows()) {
            rows.add(row.value().toMap());
        }
		
		return Result.of(rows);
	}

}
