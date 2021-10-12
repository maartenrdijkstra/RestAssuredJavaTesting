import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class Basics {
    public static void main(String[] args) {
        // Validate if Add Place API is working as expected

        // add place
        // update place with new address
        // get place and validate place with new address


        //given - input all details
        //when - submit the API . resource http method
        //then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
        .body("scope", equalTo("APP"))
        .header("server", equalTo("Apache/2.4.18 (Ubuntu)"))
        .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response); // for parsing json
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        String newAddress = "De Dam 1, Amsterdam, NL";

        //update place
        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" +  placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
        .when().put("maps/api/place/update/json")
        .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // get place
        String getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
//                .header("Content-Type", "application/json")
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().body().asString();
        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        assertEquals(actualAddress, newAddress);
    }

    @Test
    public void addPlaceFromJson() throws IOException {
        //content of file to String -> content of json file can convert into Byte -> Byte Data into String
        File jsonData = new File("src/main/resources/addPlace");

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("src/main/resources/addPlace.json"))))
                .when().post("maps/api/place/add/json").then().log().all().assertThat()
                        .statusCode(200)
                        .body("scope", equalTo("APP"))
                        .header("server", equalTo("Apache/2.4.18 (Ubuntu)"))
                        .extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(response); // for parsing json
        String placeId = js.getString("place_id");
        System.out.println(placeId);
        System.out.println(response);
    }
}

