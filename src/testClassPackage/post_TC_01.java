package testClassPackage;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class post_TC_01 {
	public static void execute() {
		// step 1 : declare bare URI and request body variable
		
		String baseURI="https://reqres.in";
		String requestBody="{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}";
		
		// step 2 expected result
		JsonPath jsprequest= new JsonPath(requestBody);
	   String req_name = jsprequest.getString("name");
	   String req_job =jsprequest.getString("job");
	   
	   //Step 3 declare base URI
	   RestAssured.baseURI="https://reqres.in";
	   
	   // step 4 configure response body
	int statusCode=given().header("Content-Type","application/json").body(requestBody).when().post("/api/users").then().extract().statusCode();
	
	String responsebody=given().header("Content-Type","application/json").body(requestBody).when().post("/api/users").then().extract().response().asString();
	System.out.println(responsebody);
	// step 5 parse the response body
	JsonPath jspresponse=new JsonPath(responsebody);
	String res_name=jspresponse.getString("name");
	String res_job=jspresponse.getString("job");
	String res_id=jspresponse.getString("id");
	String res_createdAt=jspresponse.getString("createdAt");
	
	// step6 validation
	Assert.assertEquals(res_name, req_name);
	Assert.assertEquals(res_job, req_job);
	Assert.assertNotNull(res_id);
	
	//date
	String actualdate=res_createdAt.substring(0,10);
	String currentdate=LocalDate.now().toString();
	Assert.assertEquals(actualdate, currentdate);
	   
	
	}

}
