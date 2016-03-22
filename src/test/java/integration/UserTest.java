package integration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import app.RestAPIApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestAPIApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class UserTest {

	public static final String GENDER_USER = "M";
	
	public static final String USER_NAME = "teste2";
	
	public static final int ID_USER = 1;
	
	public static final int ID_USER_FIND = 3;
	
	public static final int ID_USER_DELETE = 2;
	
	static {
		RestAssured.port = 9000;		
	}
	
	/**
	 * Retorna um usuario de acordo com o ID
	 */
	@Test
	public void getUserByID() {
		RestAssured
				.given()
				.when()
					.get("/users/"+ID_USER_FIND)
				.then()
					.statusCode(HttpStatus.OK.value())
					.body("name", Matchers.equalToIgnoringCase(USER_NAME));
	}
	
	/**
	 * Retorna todos usuarios
	 */
	@Test
	public void getAllUsers() {
		RestAssured
				.given()
				.when()
					.get("/users")
				.then()
					.statusCode(HttpStatus.OK.value())
					.body("size()", Matchers.greaterThan(0));
	}
	
	/**
	 * Retorna uma lista de usuario de acordo com o sexo
	 */
	@Test
	public void getUserByGender() {
		RestAssured
				.given()
				.when()
				.param("gender", GENDER_USER)
					.get("/users")
				.then()
					.statusCode(HttpStatus.OK.value())
					.body("size()", Matchers.greaterThan(0));
	}
	
	
	
	/**
	 * Adiciona um usuario
	 */
	@Test
	public void postUser() {
		Map<String, Object> mapNewUser = this.makePostUser();
		RestAssured
				.given()
				.contentType(ContentType.JSON)
				.content(mapNewUser)
				.when()
					.post("/users")
				.then()
					.statusCode(HttpStatus.OK.value());
	}
	
	/**
	 * Remove um usuario
	 */
	@Test
	public void deleteUser() {
		RestAssured
				.given()
				.when()
					.delete("/users/"+ID_USER_DELETE)
				.then()
					.statusCode(HttpStatus.OK.value());
	}
	
	/**
	 * Atualiza um usuario
	 */
	@Test
	public void putUser() {
		Map<String, Object> mapUserUpdated = this.makePutUser();
		RestAssured
				.given()
				.contentType(ContentType.JSON)
				.content(mapUserUpdated)
				.when()
					.put("/users/"+ID_USER)
				.then()
					.statusCode(HttpStatus.OK.value());
	}
	
	
	/*
	 * MAPS
	 */
	private Map<String, Object> makePostUser(){
		Map<String, Object> mapNewUser = new LinkedHashMap<String, Object>();
		mapNewUser.put("id", 9999);
		mapNewUser.put("name", "New Name");
		mapNewUser.put("gender", "M");
		mapNewUser.put("dateOfBirth", "25-12-2000");
		
		return mapNewUser;
	}
	
	private Map<String, Object> makePutUser(){
		Map<String, Object> mapUser = new LinkedHashMap<String, Object>();
		mapUser.put("id", ID_USER);
		mapUser.put("name", "Name Updated");
		mapUser.put("gender", "M");
		mapUser.put("dateOfBirth", "25-12-2000");
		
		
		return mapUser;
	}
}
