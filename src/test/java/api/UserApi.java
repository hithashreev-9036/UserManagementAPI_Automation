package api;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import java.util.Map;

import common.ConfigReader;

public class UserApi {
	private static final String BASE = ConfigReader.get("baseUrl");

    // POST /api/v1/users
    public static Response createUser(Map<String,Object> payload, String exampleName){
        return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName) // optional for Postman mock
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/v1/users");
    }

    // GET /api/v1/users/{id}
    public static Response getUser(int id, String exampleName){
        return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName)
            .when()
                .get("/api/v1/users/" + id);
    }

    // PUT /api/v1/users/{id}
    public static Response updateUser(int id, Map<String,Object> payload, String exampleName){
        return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .put("/api/v1/users/" + id);
    }

    // DELETE /api/v1/users/{id}
    public static Response deleteUser(int id, String exampleName){
        return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName)
            .when()
                .delete("/api/v1/users/" + id);
    }

    // GET /api/v1/users (with query params)
    public static Response listUsers(Map<String,Object> queryParams, String exampleName){
        return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName)
                .queryParams(queryParams)
            .when()
                .get("/api/v1/users");
    }

	public static Response batchQueryUsers(Map<String, Object> queryParams, String exampleName) {
		// TODO Auto-generated method stub
		return given()
                .baseUri(BASE)
                .header("x-mock-response-name", exampleName)
                .queryParams(queryParams)
            .when()
                .get("/api/v1/users");
	}
}
