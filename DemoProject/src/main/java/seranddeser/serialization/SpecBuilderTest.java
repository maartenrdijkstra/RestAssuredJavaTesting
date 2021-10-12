package seranddeser.serialization;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://www.google.com");
        p.setName("Frontline house");
        p.setTypes(Arrays.asList("Shoe Park", "shop"));
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        p.setLocation(location);

        RequestSpecification req =
                new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                        .addQueryParam("key", "qaclick123")
                        .setContentType(ContentType.JSON)
                        .build();


        RequestSpecification res = given().spec(req)
                .body(p);

        ResponseSpecification resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        Response response = res.when().post("/maps/api/place/add/json")
                .then()
                .spec(resSpec)
                .extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}
