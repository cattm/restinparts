package commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadProperties {

		public static String TEST_PROPERTY_BASE = "src/test/resources/";
		public static String PARTS_INGRESS_URL;
		public static String PARTS_API_URL;
		public static String PARTS_DB_URL;
		public static String JSON_LOAD;
		public static String JSON_VERIFY;
		
		private static boolean loaded = false;
		
		private static Properties prop = new Properties();
		public static Logger log = Logger.getLogger(LoadProperties.class.getName()); 
		 
		 public static void loadPropertyFile( String filename) throws IOException {
			 // TODO: Fix this hard wired offset in fis should just be resources/properties
	              
	         if (loaded == false) {
	        	 ///Users/marcus/Documents/ithr/cucumber only needed if running direct from cucumber file otherwise relative path works
	        	 FileInputStream fis = new FileInputStream( TEST_PROPERTY_BASE + filename);
	             prop.load(fis);
	        	 loadProperties();
	 
	        	 loaded = true;
	         }         
		 }
	 
		 private static void loadProperties() {
	         PARTS_INGRESS_URL = prop.getProperty("test.parts.ingress.url");
	         PARTS_API_URL = prop.getProperty("test.parts.api.url");
	         PARTS_DB_URL = prop.getProperty("test.parts.ingress.db");
	         JSON_LOAD = prop.getProperty("test.json.files.load");
	         JSON_VERIFY = prop.getProperty("test.json.files.verify");
		 }

}
