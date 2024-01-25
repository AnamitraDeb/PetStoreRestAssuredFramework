package petstore.tests;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import petstore.endpoints.PetModuleAPIs;
import petstore.payloadpojos.Pet;
import petstore.payloadpojos.Category;
import petstore.payloadpojos.Tag;
import petstore.utilities.ReadTestDataFromExcel;


public class PetModuleTests {
	
	Category category = new Category();
	Tag tag = new Tag();	
	Pet pet = new Pet();
	
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority =1,dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void addPet(HashMap<String,String> userInput)
	{
		category.setId(Integer.parseInt(userInput.get("category_id")));
		category.setName(userInput.get("category_name"));
		
		tag.setId(Integer.parseInt(userInput.get("tags_id")));
		tag.setName(userInput.get("tags_name"));
		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);
		
		List<String> photoUrls = new ArrayList<String>();
		photoUrls.add(userInput.get("photoUrls"));
		
		pet.setId(Integer.parseInt(userInput.get("id")));
		pet.setCategory(category);
		pet.setName(userInput.get("name"));
		pet.setPhotoUrls(photoUrls);
		pet.setTags(tags);
		pet.setStatus(userInput.get("status"));
		
		logger.info("**********Adding new Pet**********");
		
		Response response = PetModuleAPIs.addPet(pet);
		
		Pet addPetResponse = response.as(Pet.class);
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(addPetResponse.getId(), Integer.valueOf(userInput.get("id")));
		Assert.assertEquals(addPetResponse.getCategory().getId(), Integer.valueOf(userInput.get("category_id")));
		Assert.assertEquals(addPetResponse.getTags().get(0).getId(), Integer.valueOf(userInput.get("tags_id")));
		
		logger.info("**********New Pet Added**********");
	}
	
	@Test(priority=2 , dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void fetchingPetWithPetId(HashMap<String,String> userInput)
	{
		Integer petID = Integer.parseInt(userInput.get("id"));
		
		logger.info("**********Fetching Pet**********");
		
		Response response = PetModuleAPIs.getPet(petID);
		
		Pet getPetReponse = response.as(Pet.class);
		
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(getPetReponse.getId(), petID);
		Assert.assertEquals(getPetReponse.getCategory().getId(), Integer.valueOf(userInput.get("category_id")));
		Assert.assertEquals(getPetReponse.getTags().get(0).getId(), Integer.valueOf(userInput.get("tags_id")));
		
		logger.info("**********Pet Fetched**********");
	}
	
	@Test(priority=3)
	public void fetchingPetWithStatus()
	{
		List<String> status = List.of("available", "pending", "sold");
		
		logger.info("**********Fetching Pet With Status**********");
		
		Response response = PetModuleAPIs.getPetByStatus(status);

		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("**********Pet Fetched With Status**********");
	}
	
		
	@Test(priority=4 , dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void updatingPet(HashMap<String,String> userInput)
	{
		category.setId(Integer.parseInt(userInput.get("category_id")));
		category.setName(userInput.get("category_name"));
		
		tag.setId(Integer.parseInt(userInput.get("tags_id")));
		tag.setName(userInput.get("tags_name"));
		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);
		
		List<String> photoUrls = new ArrayList<String>();
		photoUrls.add(userInput.get("photoUrls"));
		
		pet.setId(Integer.parseInt(userInput.get("id")));
		pet.setCategory(category);
		pet.setName(userInput.get("updated_name"));
		pet.setPhotoUrls(photoUrls);
		pet.setTags(tags);
		pet.setStatus(userInput.get("updated_status"));
		
		logger.info("**********Updating Pet**********");
		
		Response response = PetModuleAPIs.updatePet(pet);
		
		Pet updatingPetReponse = response.as(Pet.class);
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(updatingPetReponse.getId(), Integer.valueOf(userInput.get("id")));
		Assert.assertEquals(updatingPetReponse.getName(), userInput.get("updated_name"));
		Assert.assertEquals(updatingPetReponse.getStatus(), userInput.get("updated_status"));
		
		
		logger.info("**********Pet Updated**********");
	}
	
	@Test(priority=5 , dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void updatingPetFormData(HashMap<String,String> userInput)
	{
		Integer petID = Integer.parseInt(userInput.get("id"));
		String updatedName = userInput.get("updated_name1");
		String updatedStatus = userInput.get("updated_status1");
		
		logger.info("**********Updating Pet**********");
		
		Response response = PetModuleAPIs.updatePetWithFormdata(petID, updatedName, updatedStatus);
		
		JsonPath jsonPath = new JsonPath(response.asString());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(jsonPath.getInt("code"), 200);
		Assert.assertEquals(jsonPath.getString("type"), "unknown");
		Assert.assertEquals(jsonPath.getString("message"), String.valueOf(petID));
		
		logger.info("**********Pet Updated**********");
	}
	
	@Test(priority=6 , dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void uploadingPetImage(HashMap<String,String> userInput)
	{
		Integer petID = Integer.parseInt(userInput.get("id"));
		String imagePath = System.getProperty("user.dir") + userInput.get("ImagePath");
		
		logger.info("**********Uploading Pet Image**********");
		
		Response response = PetModuleAPIs.uploadPicture(imagePath, petID);
		
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("**********Pet Image Uploaded**********");
	}
	
	@Test(priority=7 , dataProvider="getPetData", dataProviderClass=ReadTestDataFromExcel.class)
	public void deletingPet(HashMap<String,String> userInput)
	{
		Integer petID = Integer.parseInt(userInput.get("id"));
		
		logger.info("**********Deleting Pet**********");
		
		Response response = PetModuleAPIs.deletePet(petID);
		
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("**********Pet Deleted**********");
	}
}
