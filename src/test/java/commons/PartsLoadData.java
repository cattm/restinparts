package commons;

public class PartsLoadData {
	// HTTP RESPONSE CODES
	static public final int RESP_SUCCESS = 200;
	static public final int RESP_ERROR_1 = 400;
	static public final int RESP_ERROR_3 = 406;
	static public final int RESP_ERROR_4 = 415;
	
	
	//{"message":"Parts load complete"}
	static public String SUCCESS_TEXT = "{\"message\":\"Parts load complete\"}";
	static public String ERROR_1_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Missing required property: values in undefined\"}";
	static public String ERROR_2_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Expected type string but found type number in values\"}";
	static public String ERROR_3_TEXT = "{\"errorCode\":406,\"errorDescription\":\"Invalid Accept: application/xml\"}";
	static public String ERROR_4_TEXT = "{\"errorCode\":415,\"errorDescription\":\"Invalid content type (application/xop+xml).  These are valid: application/json, application/json\"}";
	static public String ERROR_5_TEXT = "{\"errorCode\":415,\"errorDescription\":\"Invalid content type (text/plain).  These are valid: application/json, application/json\"}";		
	static public String ERROR_6_TEXT = "{\"errorCode\":405,\"errorDescription\":\"Route defined in Swagger specification (/parts) but there is no defined patch operation.\"}";
	static public String ERROR_7_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Missing required property: values in undefined\"}";
	static public String ERROR_8_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Expected type string but found type integer in values\"}";

	
	
	// EXAMPLE PARTS LOAD Strings
	static public final String DB = "partsdb";
	static public final String Collection = "parts";
	static public final String  OFFSET  = "parts";
	
	
	static public final String PART1 = "";
}
