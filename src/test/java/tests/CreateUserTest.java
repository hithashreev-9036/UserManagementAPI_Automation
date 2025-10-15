package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import api.UserApi;
import common.TestListener;
import common.TestUtil;
import io.restassured.response.Response;

@Listeners(common.TestListener.class)
public class CreateUserTest {
	@Test
    public void testCreateUser_Success() {
		TestListener.getTest().log(Status.INFO, "Creating user with valid payload");
        Map<String,Object> payload = new HashMap<>();
        payload.put("username", TestUtil.randomUsername());
        payload.put("email", TestUtil.randomEmail());
        payload.put("password", TestUtil.randomPassword());

        Response res = UserApi.createUser(payload,"Success");
        res.prettyPrint();
  
        TestListener.getTest().log(Status.INFO, "Request: " + payload);
        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());
        Assert.assertEquals(res.statusCode(),201);
        Assert.assertEquals(res.jsonPath().getString("msg"),"success");
        Assert.assertNotNull(res.jsonPath().get("data.id"));
        TestListener.getTest().pass("User created successfully");
     
    }

   @Test
    public void testCreateUser_MissingEmail() {
        Map<String,Object> payload = new HashMap<>();
        payload.put("username", TestUtil.randomUsername());
        payload.put("password", TestUtil.randomPassword());
        TestListener.getTest().log(Status.INFO, "Request (missing email): " + payload);

        Response res = UserApi.createUser(payload,"Missing username");
        res.prettyPrint();
        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(),400);
        Assert.assertEquals(res.jsonPath().getString("msg"),"username is missing");
        TestListener.getTest().pass("Validated missing email scenario (expected 400).");
    }
    
    @Test
    public void testGetUser_Success() {
        // Existing user
    	TestListener.getTest().log(Status.INFO, "Fetching user with id = 1");
        Response res = UserApi.getUser(1, "Success");
        res.prettyPrint();
        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("msg"), "success");
        Assert.assertNotNull(res.jsonPath().get("data.id"));

        TestListener.getTest().pass("Get user success validated for id=1.");
    }

    @Test
    public void testGetUser_NotFound() {
        // Non-existent user
    	 TestListener.getTest().log(Status.INFO, "Fetching non-existent user id = 999");
        Response res = UserApi.getUser(999, "Not Found");
        res.prettyPrint();
        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());
        Assert.assertEquals(res.statusCode(), 404);
        Assert.assertEquals(res.jsonPath().getString("msg"), "user not found");
        TestListener.getTest().pass("Get user not-found validated for id=999.");
    }

    @Test
    public void testUpdateUserEmail_Success() {
        Map<String,Object> payload = new HashMap<>();
        payload.put("email", TestUtil.randomEmail()); // valid email

        TestListener.getTest().log(Status.INFO, "Updating user id=1 with payload: " + payload);

        Response res = UserApi.updateUser(1, payload, "Success"); // existing user
        res.prettyPrint();
        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("msg"), "success");
        TestListener.getTest().pass("Update user email success validated for id=1.");
       
    }

    @Test
    public void testUpdateUserEmail_InvalidEmail() {
        Map<String,Object> payload = new HashMap<>();
        payload.put("email", TestUtil.randomEmail()); // currently using random email (if you want invalid, set a malformed string)

        TestListener.getTest().log(Status.INFO, "Updating user id=1 with invalid-email payload: " + payload);

        Response res = UserApi.updateUser(1, payload, "InvalidEmail"); // existing user
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 400);
        Assert.assertEquals(res.jsonPath().getString("msg"), "invalid email");

        TestListener.getTest().pass("Invalid email update validated for id=1.");
    }

    @Test
    public void testUpdateUserEmail_NonExistentUser() {
        Map<String,Object> payload = new HashMap<>();
        payload.put("email", TestUtil.randomEmail()); // valid email

        TestListener.getTest().log(Status.INFO, "Updating non-existent user id=999 with payload: " + payload);

        Response res = UserApi.updateUser(999, payload, "NonExistentUser"); // non-existent user
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 404);
        Assert.assertEquals(res.jsonPath().getString("msg"), "user not found");

        TestListener.getTest().pass("Update on non-existent user validated for id=999.");
    }

    @Test
    public void testDeleteUser_Success() {
        // Delete an existing user
        TestListener.getTest().log(Status.INFO, "Deleting existing user id=1");

        Response res = UserApi.deleteUser(1, "Success");
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("msg"), "success");

        TestListener.getTest().pass("Delete user success validated for id=1.");
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        // Try deleting a non-existent user
        TestListener.getTest().log(Status.INFO, "Deleting non-existent user id=999");

        Response res = UserApi.deleteUser(999, "UserNotFound");
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 404);
        Assert.assertEquals(res.jsonPath().getString("msg"), "user not found");

        TestListener.getTest().pass("Delete non-existent user validated for id=999.");
    }

    @Test
    public void testBatchQueryUsers_Success() {
        // Valid query parameters
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", 1);
        queryParams.put("size", 10);
        queryParams.put("keyword", "test");

        TestListener.getTest().log(Status.INFO, "Batch query with params: " + queryParams);

        Response res = UserApi.batchQueryUsers(queryParams, "Success");
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("msg"), "success");

        TestListener.getTest().pass("Batch query success validated.");
    }

    @Test
    public void testBatchQueryUsers_InvalidQueryParam() {
        // Invalid query parameters
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", -1); // invalid page number
        queryParams.put("size", 1000); // too large

        TestListener.getTest().log(Status.INFO, "Batch query invalid params: " + queryParams);

        Response res = UserApi.batchQueryUsers(queryParams, "InvalidQueryParam");
        res.prettyPrint();

        TestListener.getTest().log(Status.INFO, "Response: " + res.asString());

        Assert.assertEquals(res.statusCode(), 400);
        Assert.assertEquals(res.jsonPath().getString("msg"), "invalid query parameters");

        TestListener.getTest().pass("Batch query invalid-params validated.");
    }
}
