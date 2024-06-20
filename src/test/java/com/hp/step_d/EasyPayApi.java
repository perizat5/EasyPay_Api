package com.hp.step_d;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;

public class EasyPayApi {

    static String token;
    JsonPath jp;

   @Test
    public void test01(){
       String loginBody = "{\n" +
               "  \"username\": \"user\",\n" +
               "  \"password\": \"pass\"\n" +
               "}";
        jp = given()
               .when().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .body(loginBody)
               .post("https://code-api-staging.easypayfinance.com/api/Authentication/login")
               .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginSchema.json")) //jsonSchema validation
               .statusCode(200)
               .contentType("application/json; charset=utf-8")
               .extract().jsonPath();
       token = jp.getString("token");
   }

   @Test
   public void test02(){
               given()
               .when().accept(ContentType.JSON)
               .header("Authorization", "Bearer "+token)
               .get("https://code-api-staging.easypayfinance.com/api/Application/all")
               .then()
               .statusCode(200)
               .contentType("application/json; charset=utf-8");
   }

   @Test
   public void test03(){
      jp = given()
               .when().accept(ContentType.JSON)
               .header("Authorization", "Bearer "+token)
               .contentType(ContentType.JSON)
               .body(new File("src/test/resources/easyPay.json"))
               .post("https://code-api-staging.easypayfinance.com/api/Application")
               .then()
              .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateAppSchema.json")) //jsonSchema validation
               .statusCode(200)
               .contentType("application/json; charset=utf-8")
               .extract().jsonPath();

       Map responseBody = jp.getObject("", Map.class);
        assertEquals(responseBody.get("applicationId"), jp.getInt("applicationId"));
        assertEquals(responseBody.get("name"), jp.getString("name"));
        assertEquals(responseBody.get("age"), jp.getString("age"));
        assertEquals(responseBody.get("amount"), jp.getDouble("amount"));
   }


}
