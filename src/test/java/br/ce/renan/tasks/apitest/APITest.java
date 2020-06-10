package br.ce.renan.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			.log().all()
		;
	}
	
	@Test
	public void AcionarComSucesso() {
		RestAssured
		.given()
			.body("{\"task\": \"Test via Api\",\"dueDate\": \"2020-12-12\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
		.given()
			.body("{\"task\": \"Test via Api\",\"dueDate\": \"2010-12-12\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	
}