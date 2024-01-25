package petstore.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import petstore.endpoints.Routes;

public class Specification {
	
	public static RequestSpecification getRequestSpec()
	{
		RequestSpecification requestSpecification = new RequestSpecBuilder().
				        setAccept(ContentType.JSON).
				        setBaseUri(Routes.BASE_URL).
				        setBasePath(Routes.BASE_PATH).
				        setContentType(ContentType.JSON).
				        log(LogDetail.ALL).
				        build();
		return requestSpecification;
	}
	
	public static ResponseSpecification getResponseSpec()
	{
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().
				expectContentType(ContentType.JSON).
                log(LogDetail.ALL).
                build();
		
		return responseSpecification;
	}
	

}
