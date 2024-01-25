package petstore.tests;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import petstore.endpoints.UserModuleAPIs;
import petstore.payloadpojos.User;
import petstore.utilities.ReadTestDataFromExcel;


public class UserModuleTests {
	
	User user = new User();
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority =1,dataProvider="getData", dataProviderClass=ReadTestDataFromExcel.class)
	public void creatingUser(HashMap<String,String> userInput)
	{
		user.setId(Integer.parseInt(userInput.get("id")));
		user.setUsername(userInput.get("username"));
		user.setFirstName(userInput.get("firstname"));
		user.setLastName(userInput.get("lastname"));
		user.setEmail(userInput.get("email"));
		user.setPassword(userInput.get("password"));
		user.setPhone(userInput.get("phone"));
		user.setUserStatus(Integer.parseInt(userInput.get("userStatus")));
		
		logger.info("**********Creating User**********");
		
		Response response = UserModuleAPIs.createUser(user);
		
		String createUserReponse = response.asString();
		JsonPath jsonPath = new JsonPath(createUserReponse);
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(jsonPath.getInt("code"), 200);
		Assert.assertEquals(jsonPath.getString("type"), "unknown");
		Assert.assertEquals(jsonPath.getString("message"), userInput.get("id"));
		
		logger.info("**********User Created**********");
	}
	
	@Test(priority=2 , dataProvider="getData", dataProviderClass=ReadTestDataFromExcel.class)
	public void fetchingUserwithUsername(HashMap<String,String> userInput)
	{
		String userName = userInput.get("username");
		
		logger.info("**********Fetching User**********");
		
		Response response = UserModuleAPIs.getUser(userName);
		
		User getUserReponse = response.as(User.class);
		
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(getUserReponse.getUsername(), userName);
		
		logger.info("**********User Fetched**********");
	}
	
	@Test(priority=3 , dataProvider="getData", dataProviderClass=ReadTestDataFromExcel.class)
	public void updatingUserwithUsername(HashMap<String,String> userInput)
	{
		String userName = userInput.get("username");
		
		
		user.setId(Integer.parseInt(userInput.get("id")));
		user.setUsername(userName);
		user.setFirstName(userInput.get("firstname"));
		user.setLastName(userInput.get("lastname"));
		user.setEmail(userInput.get("updatedemail"));
		user.setPassword(userInput.get("password"));
		user.setPhone(userInput.get("updatedphone"));
		user.setUserStatus(Integer.parseInt(userInput.get("updateduserStatus")));
		
		logger.info("**********Updating User**********");
		
		Response updatedresponse = UserModuleAPIs.updateUser(userName, user);
		
		Assert.assertEquals(updatedresponse.statusCode(), 200);
		
       Response getResponse = UserModuleAPIs.getUser(userName);
		
		User getUserReponse = getResponse.as(User.class);
		
		
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertEquals(getUserReponse.getUsername(), userName);
		
		Assert.assertEquals(getUserReponse.getEmail(), userInput.get("updatedemail"));
		Assert.assertEquals(getUserReponse.getPhone(), userInput.get("updatedphone"));
		Assert.assertEquals(Integer.toString(getUserReponse.getUserStatus()), userInput.get("updateduserStatus"));
		
		logger.info("**********User Updated**********");
	}
	
	@Test(priority=4 , dataProvider="getData", dataProviderClass=ReadTestDataFromExcel.class)
	public void deletingUserwithUsername(HashMap<String,String> userInput)
	{
		String userName = userInput.get("username");
		
		logger.info("**********Deleting User**********");
		
		Response response = UserModuleAPIs.deleteUser(userName);
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("**********User Deleted**********");
		
	}
}
