package petstore.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import petstore.payloadpojos.User;
import petstore.specifications.Specification;


public class UserModuleAPIs {
	
	    public static Response createUser(User payload)
	    {
		  Response response = given(Specification.getRequestSpec()).
				                  body(payload).
		                     when().
		                          post(Routes.USERS).
		                     then().
		                          spec(Specification.getResponseSpec()).
		                          extract().
		                          response();
	     return response;
	    }
	
		public static Response getUser(String userName)
		{
			Response response = given(Specification.getRequestSpec()).
					              pathParam("username", userName).
		                        when().
		                           get(Routes.USERS + "/" + "{username}").
		                        then().
		                          spec(Specification.getResponseSpec()).
		                          extract().
		                          response();
		   return response;
	    }
	
	   public static Response updateUser(String userName, User payload)
	   {
		  Response response = given(Specification.getRequestSpec()).
				               pathParam("username", userName).
				               body(payload).
				             when().
				               put(Routes.USERS + "/" + "{username}").
				             then().
				              spec(Specification.getResponseSpec()).
	                          extract().
	                          response();
		
		  return response;
	  }
	
	   public static Response deleteUser(String userName)
	   {
		  Response response = given(Specification.getRequestSpec()).
				                   pathParam("username", userName).
                            when().
                                   delete(Routes.USERS + "/" + "{username}").
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();
				
		  return response;
	   }
}
