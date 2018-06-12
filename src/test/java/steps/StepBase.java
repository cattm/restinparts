package steps;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import commons.LoadProperties;
import cucumber.api.Scenario;
import utils.ErrorCollector;
import utils.JsonUtils;

public class StepBase {
	static Logger log = Logger.getLogger(StepBase.class.getName());
	public static String propertyFile = "test.properties";
	
	
	private HttpResponse response;
	private String bodyText = "";
	
	
	
	public void setUp(Scenario scenario) throws Exception {
		log.info("Steps SetUp");
	    LoadProperties.loadPropertyFile(propertyFile);
	    ErrorCollector.setVerify(true);
	    
	   
	}
	
	public void tearDown() {
		log.info("Steps TearDown");
		if (ErrorCollector.failedVerification()) {
			  log.error("There are verification Errors to review");
			  //Fail the scenario as the individual text errors constitute a fail overall
			  // even though the scenario probable completed ok
			  fail(ErrorCollector.getVerificationFailures());
		}
	}
	
	
	/*
	 * Mongo DB utility to abstract and refactor
	 */
	public String ReadMondgo(String key, String value) {
		String result = "";
		try {
			 String url = LoadProperties.PARTS_DB_URL;
			 MongoClientURI uri = new MongoClientURI(url,MongoClientOptions.builder().cursorFinalizerEnabled(false));
			 MongoClient mongoClient = new MongoClient(uri);
            
	         @SuppressWarnings("deprecation")
			List<String> databases = mongoClient.getDatabaseNames();
	             
	         for (String dbName : databases) {
	        	 log.info("- Database: " + dbName);
	                 
	             @SuppressWarnings("deprecation")
				DB db = mongoClient.getDB(dbName);
	                 
	             Set<String> collections = db.getCollectionNames();
	             for (String colName : collections) {
	                 log.info("\t + Collection: " + colName);
	             }
	         }
	         
	         @SuppressWarnings("deprecation")
	         // TODO: This is a hard code to remove
			 DB db = mongoClient.getDB("partsdb");
	         DBCollection table = db.getCollection("parts");
	         
	         /**** Find and display ****/
	     	BasicDBObject searchQuery = new BasicDBObject();
	     	searchQuery.put(key, value);

	     	DBCursor cursor = table.find(searchQuery);

	     	while (cursor.hasNext()) {
	     		BasicDBObject obj = (BasicDBObject) cursor.next();
	     		JSON json =new JSON();
	            String serialize = json.serialize(obj);
	            log.info(" found " + serialize);
	            result = serialize;
	     		//log.info("Found row " + cursor.next().toString());
	     	}
	        mongoClient.close();
		
		}
		catch (MongoException e) {
			e.printStackTrace();
		}
		return result;
	}
	
		
	
	JSONObject StripMongoRecord(String record) {
		//remove the OID
		JSONObject tmp = new JSONObject(record);
        Iterator<String> keys = tmp.keys();
        Map<String, Object> jsonMap = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!key.contentEquals("_id")) {
            	jsonMap.put(key, tmp.get(key));
            }
        }
        JSONObject obj = new JSONObject(jsonMap);
        return obj;
		
	}
	boolean checkDBRecord(JSONObject dbvalue, JSONObject jsonloaded) {	
		return JsonUtils.areEqual(dbvalue, jsonloaded);
	}
	
	
	String SelectPartsJsonString(String data) {
		 // partsload_api_scene_1_default
		return JsonUtils.jsonAsStringFromFile(LoadProperties.JSON_LOAD + data + ".json");

	}
}
