package seranddeser.serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class SerializedTest {
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

        Response res = given().queryParam("key", "qaclick123")
                .body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat()
                .statusCode(200)
                .extract().response();
        String response = res.asString();
        System.out.println(response);
    }
}
