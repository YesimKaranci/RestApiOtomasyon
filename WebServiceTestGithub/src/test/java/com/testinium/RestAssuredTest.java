package com.testinium;

import com.sun.org.glassfish.gmbal.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class RestAssuredTest
{
  private RequestSpecification requestSpecification;

    @Before
    @Description("Base Url tanımlama")
    public void setUp(){
        RestAssured.baseURI="https://api.github.com";
        requestSpecification = RestAssured.given();
        requestSpecification
                .auth()
                        .oauth2("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");
    }

    @Test
    @Description("Status'un 200(Success) gelme kontrolü")
    public void testDoRequestForStatusCode(){
        RestAssured.baseURI="https://api.github.com";
        requestSpecification = RestAssured.given();
        requestSpecification.
                when().get(baseURI).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @Description("Github Repository oluşturma")
    public void createRepository(){
        String postData = "{\n" +
                "    \"name\": \"TestiniumRestApı\",\n" +
                "    \"description\": \"This is your first repository\",\n" +
                "    \"homepage\": \"https://github.com\",\n" +
                "    \"private\": false,\n" +
                "    \"has_issues\": true,\n" +
                "    \"has_projects\": false,\n" +
                "    \"has_wiki\": true\n" +
                "}";

        given()
                .auth().oauth2("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")
                .header("Accept","application/vnd.github.v3+json")
                .and()
                .body(postData)
                .when()
                .post("https://api.github.com/user/repos")
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    @Test
    public void getRepository(){
        Response response = requestSpecification.get("/user/repos");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(bodyAsString);
    }

    @Test
    public void updateRepository(){
        String updateData = "{\n" +
                "    \"name\": \"GithubApıTest\"\n" +
                "}";

        Response response = requestSpecification
                .body(updateData)
                .patch("/repos/YesimKaranci/TestiniumRestApı");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(bodyAsString);
    }

}
