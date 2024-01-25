package petstore.tests;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import petstore.endpoints.StoreModuleAPIs;
import petstore.payloadpojos.Store;
import petstore.utilities.ReadTestDataFromExcel;


public class StoreModuleTests {
	
	Store store = new Store();
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority =1,dataProvider="getStoreData", dataProviderClass=ReadTestDataFromExcel.class)
	public void placeOrder(HashMap<String,String> userInput)
	{
		store.setId(Integer.parseInt(userInput.get("id")));
		store.setPetId(Integer.parseInt(userInput.get("petId")));
		store.setQuantity(Integer.parseInt(userInput.get("quantity")));
		store.setShipDate(userInput.get("shipDate"));
		store.setStatus(userInput.get("status"));
		store.setComplete(Boolean.parseBoolean(userInput.get("complete")));
		
		
		logger.info("**********Place Order**********");
		
		Response response = StoreModuleAPIs.placeOrder(store);
		
		Store placeOrderReponse = response.as(Store.class);
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(placeOrderReponse.getId(), Integer.valueOf(userInput.get("id")));
		Assert.assertEquals(placeOrderReponse.getPetId(), Integer.valueOf(userInput.get("petId")));
		Assert.assertEquals(placeOrderReponse.getComplete(), Boolean.valueOf(userInput.get("complete")));
		
		logger.info("**********Order Placed**********");
	}
	
	@Test(priority=2 , dataProvider="getStoreData", dataProviderClass=ReadTestDataFromExcel.class)
	public void fetchingOrderwithOrderID(HashMap<String,String> userInput)
	{
		Integer orderID = Integer.parseInt(userInput.get("id"));
		
		logger.info("**********Fetching Order**********");
		
		Response response = StoreModuleAPIs.getOrder(orderID);
		
		Store getOrderReponse = response.as(Store.class);
		
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(getOrderReponse.getId(), orderID);
		
		logger.info("**********Order Fetched**********");
	}
	
	@Test(priority=3 , dataProvider="getStoreData", dataProviderClass=ReadTestDataFromExcel.class)
	public void deletingOrderwithOrderID(HashMap<String,String> userInput)
	{
		Integer orderID = Integer.parseInt(userInput.get("id"));
		
		logger.info("**********Deleting Order**********");
		
		Response deletedResponse = StoreModuleAPIs.deleteOrder(orderID);
		JsonPath jsonPath = new JsonPath(deletedResponse.asString());
		
		Assert.assertEquals(deletedResponse.statusCode(), 200);
		Assert.assertEquals(jsonPath.getInt("code"), 200);
		Assert.assertEquals(jsonPath.getString("type"), "unknown");
		Assert.assertEquals(jsonPath.getString("message"), String.valueOf(orderID));
		
		
		logger.info("**********Order deleted**********");
	}
	
	@Test(priority=4)
	public void petInventory()
	{
		
		
		logger.info("**********Fetching Inventory Details**********");
		
		Response response = StoreModuleAPIs.getPetInventory();
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("**********Fetched Inventory Details**********");
		
	}
}
