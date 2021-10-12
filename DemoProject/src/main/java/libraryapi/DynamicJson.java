package libraryapi;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "booksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166/";
        String response = given().header("Content-Type", "application/json")
//                .queryParam("key", "qaclick123")
                .body(Payload.addBook(aisle, isbn))
                .when()
                .post("Library/Addbook.php")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response().asString();
        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.getString("ID");
        System.out.println(id);
    }

    @Test(dataProvider = "booksData")
    public void deleteBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166/";
        given().header("Content-Type", "application/json")
                .body(Payload.deleteBook(aisle, isbn))
                .when()
                .post("Library/DeleteBook.php")
                .then().log().all()
                .assertThat().statusCode(200);
    }


    @DataProvider(name = "booksData")
    public Object[][] getData() {
        return new Object[][]{{"ihsdf", "73654"}, {"jhsdf", "26753"}, {"oiwes", "9384"}};
    }
}
