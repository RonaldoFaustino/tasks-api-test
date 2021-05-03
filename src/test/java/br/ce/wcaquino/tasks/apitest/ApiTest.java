package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200);
	}

	@Test
	public void deveAdicionarTarefasComSucesso() {
		RestAssured.given()
			.body("{\"task\" : \"Teste via Api\",\r\n" + 
					" \"dueDate\" : \"2021-04-26\"\r\n" + 
					"}")
			.contentType(ContentType.JSON)
			.log().all()
		.when()
			.post("/todo")
		
		.then()
			.log().all()
			.statusCode(400);
	}
	
	@Test
	public void naoDeveAdicionarTarefasInvalida() {
		RestAssured.given()
			.body("{\"task\" : \"Teste via Api\",\r\n" + 
					" \"dueDate\" : \"2010-12-30\"\r\n" + 
					"}")
			.contentType(ContentType.JSON)
			.log().all()
		.when()
			.post("/todo")
		
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
