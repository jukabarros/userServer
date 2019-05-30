package integration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import api.App;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

	public static final String GENDER_USER = "M";
	public static final String USER_NAME = "teste2";
	public static final int ID_USER = 1;
	public static final int ID_USER_FIND = 3;
	public static final int ID_USER_DELETE = 2;

	@Value("${local.server.port}")
	private int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
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
		.body(mapNewUser)
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
		.body(mapUserUpdated)
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
