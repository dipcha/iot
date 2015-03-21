package com.project.main;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.project.model.DeviceModel;

@Path("/devices")
public class RESTApplication {
		
		@GET
		@Path("/get")
		@Produces("application/json")
		public ArrayList<DeviceModel> getDeviceDetails() throws UnknownHostException {
			Device device = new Device();
			ArrayList<DeviceModel> dm = device.getDBData();
		    return dm;
		}
		
		
		@Path("{c}")
		@GET
		@Produces("application/xml")
		public String convertCtoFfromInput(@PathParam("c") Double c) {
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = ((celsius * 9) / 5) + 32;
		 
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
		}
		
		
}