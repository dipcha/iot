package com.project.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.project.model.DeviceModel;

@Path("device")
public class Device {

	DeviceModel device;
	
	public JSONObject getJsonFromUrl() {
		JSONObject json = null;
		try { 
            URL url = new URL("http://Default-Environment-qnbzq3unpn.elasticbeanstalk.com/1/getTemp"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); 
            connection.setInstanceFollowRedirects(false); 
            connection.setRequestMethod("GET"); 
            connection.setRequestProperty("Content-Type", "application/json");

            //input = connection.getInputStream();
            
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
             builder.append(line);
            }

            json = new JSONObject(builder.toString());
            connection.disconnect(); 
        } catch(Exception e) { 
        	System.out.println("Exception in getJsonFromUrl" + e);
        }
		
		return json;
	}

	public DeviceModel readFrom(JSONObject json) {
		device = new DeviceModel();
		try {
				device.setDevID((int) json.get("devID"));
				device.setTemperature((float) json.get("temperature"));
				Date date = new Date();
				device.setDate((new Timestamp(date.getTime())).toString());
			} catch (Exception e) {
				System.out.println("Exception in readFrom" + e);
		}
		return device;
	}

	/*
	public DeviceModel readFrom(InputStream in) {
		device = new DeviceModel();
		JsonParser parser = null;
		try {
				parser = Json.createParser(in);
				while (parser.hasNext()) {
				switch (parser.next()) {
					case KEY_NAME:
						String key = parser.getString();
						parser.next();
						switch (key) {
							case "id":
								device.setId(parser.getString());
								break;
							case "temperature":
								device.setTemperature(parser.getString());
								break;
							case "today":
								Date date = new Date();
								device.setDate((new Timestamp(date.getTime())).toString());
								break;
							default:
								break;
						}
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in readFrom" + e);
		}
		parser.close();
		return device;
	}
*/	

	@GET
	@Path("/updateData")
	public void putDeviceDetails() {
		JSONObject json = getJsonFromUrl();
		DeviceModel device = readFrom(json);
		MongoDBFunctions mongoDB = new MongoDBFunctions();
		//mongoDB.insertNewDocument(device);
	}

	public ArrayList<DeviceModel> getDBData() throws UnknownHostException{	
		
		String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
		MongoClientURI uri = new MongoClientURI(texturi);
		MongoClient mongo = new MongoClient(uri);
		
		ArrayList<DeviceModel> list = new ArrayList<DeviceModel>();
		DB db = mongo.getDB("iot");
		DBCollection coll = db.getCollection("deviceDetails");
		DBCursor cursor = coll.find();
		DeviceModel devices = null;
		while(cursor.hasNext()){
			   System.out.println("inside cursor");
			   DBObject obj = cursor.next();
			
			   int devID = Integer.valueOf(obj.get("id").toString());
			   float temperature = Float.parseFloat((obj.get("temperature").toString()));
			   String devicetype = obj.get("deviceType").toString();
			   String date = obj.get("date").toString();
					   
			   devices = new DeviceModel();
			   devices.setDevID(devID);
			   devices.setTemperature(temperature);
			   devices.setDate(date);
			   devices.setDevicetype(devicetype);
			   list.add(devices);
		}
		return list;
		}	
//	
//	@GET
//	@Path("/getAll")
//	@Produces("application/json")
//	public DeviceModel getAllDeviceDetails() {
//		MongoDBFunctions mongoDB = new MongoDBFunctions();
//		mongoDB.getAllDocuments();
//	    return device;
//	}
}