package petstore.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import petstore.payloadpojos.Store;
import petstore.specifications.Specification;


public class StoreModuleAPIs {
	
	public static Response placeOrder(Store payload)
	{
		Response response = given(Specification.getRequestSpec()).
				                   body(payload).
				            when().
				                   post(Routes.STORE_ORDER).
				            then().
				                   spec(Specification.getResponseSpec()).
				                   extract().
				                   response();
		
	   return response;
	}
	
	public static Response getOrder(Integer orderID)
	{
		
		Response response = given(Specification.getRequestSpec()).
				                   pathParam("orderID", orderID).
				            when().
				                   get(Routes.STORE_ORDER+ "/" + "{orderID}").
				            then().
				                   spec(Specification.getResponseSpec()).
			                       extract().
			                       response();
				
	    return response;
	}
	
	public static Response deleteOrder(Integer orderID)
	{
		Response response = given(Specification.getRequestSpec()).
                                   pathParam("orderID", orderID).
                            when().
                                   delete(Routes.STORE_ORDER+ "/" + "{orderID}").
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();

      return response;
	}
	
	public static Response getPetInventory()
	{
		Response response = given(Specification.getRequestSpec()).
                            when().
                                   get(Routes.STORE_INVENTORY).
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();

        return response;
	}
}
