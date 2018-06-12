package utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import steps.PartsLoadStepsS1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


public class JsonUtils {
	static Logger log = Logger.getLogger(JsonUtils.class.getName());
	
	public static boolean areEqual(Object ob1, Object ob2) throws JSONException {
        Object obj1Converted = convertJsonElement(ob1);
        Object obj2Converted = convertJsonElement(ob2);
        return obj1Converted.equals(obj2Converted);
    }

    private static Object convertJsonElement(Object elem) throws JSONException {
    	
        if (elem instanceof JSONObject) {
            JSONObject obj = (JSONObject) elem;
            Iterator<String> keys = obj.keys();
            Map<String, Object> jsonMap = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                jsonMap.put(key, convertJsonElement(obj.get(key)));
            }
            return jsonMap;
        } else if (elem instanceof JSONArray) {
            JSONArray arr = (JSONArray) elem;
            Set<Object> jsonSet = new HashSet<>();
            for (int i = 0; i < arr.length(); i++) {
                jsonSet.add(convertJsonElement(arr.get(i)));
            }
            return jsonSet;
        } else {
            return elem;
        }
    }	
    
    
    private static String readFile(String filename) {
	    String result = "";
	    try {
	    	File fileDir = new File(filename);

			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));

			String str;

			while ((str = in.readLine()) != null) {
			    System.out.println(str);
			    result += str;
			}

	        	in.close();
	    } 
	    	catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
		    }
		    catch (IOException e)
		    {
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
				System.out.println(e.getMessage());
		    }

	    return result;
	        
	}
	
    public static JSONObject jsonFromFile(String file) {
		JSONObject json = null;
		String tmp =  readFile(file); 
		json = new JSONObject(tmp);
		return json;
    }
    
    public static String jsonAsStringFromFile(String file) {
    	String strJson = null;
    	strJson = readFile(file); 
    	return strJson;
    }
	/*
	public static JSONObject jsonFromFile(String file) {
		JSONObject json = null;;
		File f = new File(file);
		if (f.exists()) {
			 	InputStream is;
				try {
					is = new FileInputStream(file);
					 String jsonTxt = IOUtils.toString(is);
			         json = new JSONObject(jsonTxt);   
				} catch (FileNotFoundException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
	           
		}
		return json;
	}
	

	public static String jsonAsStringFromFile(String file) {
		//log.info("searching for : " + file);
		String strJson = null;;
		File f = new File(file);
		if (f.exists()) {
			 	InputStream is;
				try {
					is = new FileInputStream(file);
					 strJson = IOUtils.toString(is);
					
			            
				} catch (FileNotFoundException e) {
					log.info("File not found : " + file);
					e.printStackTrace();
				} catch (IOException e) {
					log.info("File IO Exception : " + file);
					e.printStackTrace();
				}
	           
		}
		//log.info(strJson);
		return strJson;
	}
*/	
	public static JSONObject getJsonElementFromKey(String keyvalue, JSONArray jsonarray) {
		JSONObject result = null;
		boolean found = false;
		// so we have an array - we want the record that matches the key
		for (int i = 0; i < jsonarray.length() && !found; i++) {
			  JSONObject tmp = jsonarray.getJSONObject(i);
			  String str = (String) tmp.get("partId").toString();
			  if (str.contentEquals(keyvalue)) {
				  result = tmp;
				  found = true;
			  }
		}
		return result;
	}
	
	public int getArraySize(Object array) {
		if (isJsonArray(array)) {
			return ((JSONArray) array).length();
		} else {
			return 0;
		}
	}
	
	// method currently assume the object is at least a JSON Object
	public static boolean isJsonObject(Object jsonVal){
        boolean h = false;
        try {
            JSONObject j1=(JSONObject)jsonVal;
            h=true;
        } catch (Exception e) {
        	log.info(e);
            //e.printStackTrace();
            h=false;
        }
        return h;
	}
	
	public static boolean isJsonArray(Object jsonval){
        boolean h = false;
        try {
            JSONArray j1=(JSONArray)jsonval;
            h=true;
        } catch (Exception e) {
        	log.info(e);
            //e.printStackTrace();
            h=false;
        }
        return h;
	}
	
	public static JSONObject getJsonLinkFromKey(String keyvalue, JSONArray jsonarray) {
		JSONObject result = null;
		boolean found = false;
		// so we have an array - we want the record that matches the key
		for (int i = 0; i < jsonarray.length() && !found; i++) {
			  JSONObject tmp = jsonarray.getJSONObject(i);
			  String str = (String) tmp.get("method").toString();
			  
			  if (str.contentEquals(keyvalue)) {
				  result = tmp;
				  found = true;
				  log.info(str);
			  }
		}
		return result;
	}
	
	
	public static JSONObject getLink(Object jsonobj, String link) {
		JSONObject tmp = null;
		// assume it the whole payload 
		if (isJsonObject( jsonobj)) {
			// find the _links array
			JSONObject xx = (JSONObject) jsonobj;
			JSONArray links = xx.getJSONArray("_links");
			tmp = getJsonLinkFromKey(link.toLowerCase(), links);
		} else { log.info("NOT an JSON object"); }
		
		return tmp;
	}
	
	public static String getErrorDescription(String from) {
		JSONObject error = new JSONObject(from);
		return error.getString("errorDescription").toString();
	}
}
