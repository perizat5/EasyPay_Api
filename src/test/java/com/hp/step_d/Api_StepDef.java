package com.hp.step_d;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.pojo.AppliedFacets;
import com.hp.pojo.JobSearch;
import com.hp.utilMetods.*;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class Api_StepDef {

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;
    JsonPath jsonPath;
    Map<String, Object> dbMap;
    Map<String, Object> bodyMap;

    @Given("Accept header is {string}")
    public void accept_header_is(String string) {
        givenPart = RestAssured.given().log().uri()
                .accept(ContentType.JSON);
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {

        givenPart.contentType(contentType);
    }

    @And("Request body contains the following information:")
    public void request_body_contains_the_following_information(Map<String, Object> bodyReq) {
        givenPart.body(bodyReq);
    }

    @When("I send Post request to the {string} endpoint")
    public void i_send_post_request_to_the_endpoint(String endpoint) {
        response=givenPart.when().post(Config_Reader.getProperty("apiUri") + endpoint)
                .prettyPeek();
        thenPart = response.then();
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(int statusCode) {
        thenPart.statusCode(statusCode);
    }

    @Then("Response Content type header should be {string}")
    public void response_content_type_header_should_be(String contentType) {
        thenPart.contentType(contentType);
    }

    @When("I retrieve information from the database")
    public void i_retrieve_information_from_the_database() {
        String query = "select * from JobPostings where location='Tyler'";
        DataBase_Utils.runQuery(query);
        dbMap = DataBase_Utils.getRowMap(1);
        //also we can retrieve from database all results using List<Map<String, Object>>
    }

    @Then("the API response and database information should be equal")
    public void the_api_response_and_database_information_should_be_equal() {
        Map<String, Object> apiMap = response.path("jobPostings[0]");
        assertEquals(apiMap, dbMap);
        System.out.println("apiMap = " + apiMap);
    }

/////////
    @DisplayName("With Hamcrest Matchers. Json Schema Validation")
    @Test
    public void test01() {
        String endpoint = "/wday/cxs/hpinc/hpinc/jobs";
        bodyMap = new LinkedHashMap<>();
        bodyMap.put("appliedFacets", new HashMap<>());
        bodyMap.put("limit", 20);
        bodyMap.put("offset", 0);
        bodyMap.put("searchText", "Tyler");
        System.out.println("bodyMap = " + bodyMap);
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post(Config_Reader.getProperty("apiUri") + endpoint).prettyPeek()
                .then().statusCode(200)
                //   .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("HPSchema.json"))
                .contentType("application/json")
                .body("jobPostings.title", everyItem(notNullValue()))
                .body("jobPostings.locationsText", everyItem(equalTo("6 Locations")))
                .body("jobPostings[3].title", equalTo("Well Control Technician"));
    }


    @DisplayName("With POJO")
    @Test
    public void test03() {
        JobSearch jobSearch = new JobSearch();
        jobSearch.setAppliedFacets(new AppliedFacets());
        jobSearch.setLimit(20);
        jobSearch.setOffset(0);
        jobSearch.setSearchText("Tyler");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(jobSearch);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String endpoint = "/wday/cxs/hpinc/hpinc/jobs";
        JsonPath js = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Config_Reader.getProperty("apiUri") + endpoint)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();

        String expThirdJobTitle = js.getString("jobPostings[2].title");
        assertEquals("Rig Electrician", expThirdJobTitle);
        System.out.println("expThirdJobTitle = " + expThirdJobTitle);
    }

    @DisplayName("Validate DB/API using JsonPath")
    @Test
    public void test04() {
        String endpoint = "/wday/cxs/hpinc/hpinc/jobs";
        JsonPath js = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post(Config_Reader.getProperty("apiUri") + endpoint).prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .extract().jsonPath();
        //API
        Map<String, Object> apiMap = js.getMap("jobPostings[0]");
        System.out.println("apiMap = " + apiMap);

        //DATABASE
        String query = "select * from JobPostings where location='Tyler'";
        DataBase_Utils.createConnection();
        DataBase_Utils.runQuery(query);
        Map<String, Object> dbMap = DataBase_Utils.getRowMap(1);
        assertEquals(apiMap, dbMap);
        DataBase_Utils.destroy();

/*
 "title": "U.S. Rig Based Positions",
            "externalPath": "/job/Odessa-TX-USA/US-Rig-Based-Positions_REQ-1",
            "locationsText": "6 Locations",
            "postedOn": "Posted 30+ Days Ago",
            "bulletFields": [
                "REQ-1"
            ]
 */
    }

    

}
