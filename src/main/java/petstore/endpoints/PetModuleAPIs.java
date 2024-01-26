package petstore.endpoints;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.payloadpojos.Pet;
import petstore.specifications.Specification;


public class PetModuleAPIs {
	
	public static Response addPet(Pet payload)
	{
		Response response = given(Specification.getRequestSpec()).
				                   body(payload).
				            when().
				                   post(Routes.PET).
				            then().
				                   spec(Specification.getResponseSpec()).
				                   extract().
				                   response();
		
	   return response;
	}
	
	public static Response getPet(Integer petID)
	{
		
		Response response = given(Specification.getRequestSpec()).
				                   pathParam("petID", petID).
				            when().
				                   get(Routes.PET + "/" + "{petID}").
				            then().
				                   spec(Specification.getResponseSpec()).
			                       extract().
			                       response();
				
	    return response;
	}
	
	public static Response getPetByStatus(List<String> status)
	{
		
		Response response = given(Specification.getRequestSpec()).
				                   queryParam("status", status).       
				            when().
				                   get(Routes.PET + Routes.GET_PET_BY_STATUS).
				            then().
				                   spec(Specification.getResponseSpec()).
			                       extract().
			                       response();
				
	    return response;
	}
	
	public static Response updatePet(Pet payload)
	{
		
		Response response = given(Specification.getRequestSpec()).
				                   body(payload).       
				            when().
				                   put(Routes.PET).
				            then().
				                   spec(Specification.getResponseSpec()).
			                       extract().
			                       response();
				
	    return response;
	}
	
	
	
	public static Response deletePet(Integer petID)
	{
		Response response = given(Specification.getRequestSpec()).
                                   pathParam("petID", petID).
                            when().
                                   delete(Routes.PET + "/" + "{petID}").
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();

      return response;
	}
	
	public static Response updatePetWithFormdata(Integer petID, String updatedName, String updatedStatus)
	{
		Response response = given().
				                    spec(Specification.getRequestSpecForUrlEncoded()).
				                    pathParam("petID", petID).
                                    formParams("name", updatedName).
                                    formParam("status", updatedStatus).
                                    log().all().
                            when().
                                   post(Routes.PET + "/" + "{petID}").
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();

      return response;
	}
	
	public static Response uploadPicture(String imagePath, Integer petID)
	{
		Response response = given().
				                    spec(Specification.getRequestSpecForMultipart()).
				                    pathParam("petID", petID).
				                    multiPart("file", new File(imagePath)).
				                    log().all().
                            when().
                                   post(Routes.PET + "/" + "{petID}" + Routes.UPLOAD_IMAGE).
                            then().
                                   spec(Specification.getResponseSpec()).
                                   extract().
                                   response();

      return response;
	}
}