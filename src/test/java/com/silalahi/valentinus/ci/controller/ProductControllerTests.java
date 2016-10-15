package com.silalahi.valentinus.ci.controller;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.*;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.silalahi.valentinus.ci.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = { "/mysql/hapus-data.sql", "/mysql/contoh-product.sql" })
public class ProductControllerTests {

	private static final String BASE_URL = "/api/product";

	@Value("${local.server.port}")
	int serverPort;

	@org.junit.Before
	public void setup() {
		RestAssured.port = serverPort;
	}

	@Test
	public void testFindAll() {
		get(BASE_URL + "/")
		.then()
		.body("totalElements", equalTo(1))
		.body("content.id", hasItems("xyz890"));
	}

	@Test
	public void testSave() {
		Product p = new Product();
		p.setKode("A-101");
		p.setNama("Barang A Test 101");
		p.setHarga(BigDecimal.valueOf(1001.09));
		
		given()
		.body(p)
		.contentType(ContentType.JSON)
		.when()
		.post(BASE_URL+"/")
		.then()
		.statusCode(201)
		.header("Location", containsString(BASE_URL+"/"))
		.log()
		.headers();
		
		//nama null
		Product px = new Product();
		px.setKode("T-001");
		
		given()
		.body(px)
		.contentType(ContentType.JSON)
		.when()
		.post(BASE_URL+"/")
		.then()
		.statusCode(400);
		
		//kode jurang dari 3 huruf
		Product px1 = new Product();
		px1.setKode("T-");
		px1.setNama("Test Product");
		p.setHarga(BigDecimal.valueOf(900));
		
		given()
		.body(px1)
		.contentType(ContentType.JSON)
		.when()
		.post(BASE_URL+"/")
		.then()
		.statusCode(400);
	}

	@Test
	public void testFindById() {
		get(BASE_URL+"/xyz098")
		.then()
		.statusCode(200)
		.body("id", equalTo("xyz098"))
		.body("kode", equalTo("A-100"));
	}

	@Test
	public void testUpdate() {
		Product p = new Product();
		p.setKode("PX-009");
		p.setNama("Product 909");
		p.setHarga(BigDecimal.valueOf(2000));
		
		given()
		.body(p)
		.contentType(ContentType.JSON)
		.when()
		.put(BASE_URL+"/xyz098")
		.then()
		.statusCode(200);
		
		get(BASE_URL+"/xyz098")
		.then()
		.statusCode(200)
		.body("id", equalTo("xyz098"))
		.body("kode", equalTo("PX-009"))
		.body("nama", equalTo("Product 909"));
	}

	@Test
	public void testDelete() {
		delete(BASE_URL+"/xyz098")
		.then()
		.statusCode(200);
		
		get(BASE_URL+"/xyz098")
		.then()
		.statusCode(404);
	}

}
