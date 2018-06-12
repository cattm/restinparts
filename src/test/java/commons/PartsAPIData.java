package commons;

public class PartsAPIData {

	static public final int RESP_SUCCESS = 200;
	static public final int RESP_ERROR_1 = 400;
	static public final int RESP_ERROR_3 = 406;
	static public final int RESP_ERROR_4 = 415;
	
	static public String ERROR_1_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Missing required property: values in undefined\"}";
	static public String ERROR_2_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Expected type string but found type number in values\"}";
	static public String ERROR_3_TEXT = "{\"errorCode\":406,\"errorDescription\":\"Invalid Accept: application/xml\"}";
	static public String ERROR_4_TEXT = "{\"errorCode\":415,\"errorDescription\":\"Invalid content type (application/xop+xml).  These are valid: application/json, application/json\"}";
	static public String ERROR_5_TEXT = "{\"errorCode\":415,\"errorDescription\":\"Invalid content type (text/plain).  These are valid: application/json, application/json\"}";
	static public String ERROR_6_TEXT = "{\"errorCode\":405,\"errorDescription\":\"Route defined in Swagger specification (/parts) but there is no defined patch operation.\"}";
	static public String ERROR_7_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Missing required property: values in undefined\"}";
	static public String ERROR_8_TEXT = "{\"errorCode\":400,\"errorDescription\":\"Expected type string but found type integer in values\"}";

	

	
	static public final String EXAMPLE_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiQWJoYW5nZSBERDEiLCJlbWFpbEFkZHJlc3MiOiJhYmhhbmdlZGQxQGdtYWlsLmNvbSIsIm9pZCI6ImU2MGNkOTgzLWZmNzMtNGNjMy05NjE4LTg1YjQzNjZlODU1ZiIsImlhdCI6MTQ4NDU2MzY5OH0.oOOpNTFPnJ4C5HTb93DDXeWUsy48dk9SGgCQerRoL0k";
	static public final String DB = "partsdb";
	static public final String OFFSET = "parts";
	static public final String PARAM = "characteristics";
	
	
	static public final String  CFILTER_1 = "SINGLE_KEY%7CONE";
	static public final String  CFILTER_3 = "PAGE_BOUNDARY%7CTHREE";
	static public final String  CFILTER_4 = "PAGE_BOUNDARY%7CFOUR";
	static public final String  CFILTER_5 = "PAGE_BOUNDARY%7CFIVE";
	static public final String  CFILTER_6 = "PAGE_BOUNDARY%7CSIX";
	static public final String  CFILTER_MULTI_4 = "4_PAGE%7CFOUR";
	static public final String  CFILTER_MULTI_16 = "LARGE_PAGE%7CLARGE_02";
	
	
	static public final String VALIDATE_JSON_1 = "";
	
	static public final String ERROR_MESSAGE_415 = "Version requested XX is not supported (latest is YY)";
	
}
