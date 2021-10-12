package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080/";
        SessionFilter session = new SessionFilter();

        //Login scenario
        String response = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\": \"maartenrdijkstra\",\n" +
                        "    \"password\": \"Maarten120%\"\n" +
                        "}").log().all()
                .filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();

        String expectedMessage = "Hi, how are you?";
        // Add comment
        given().pathParam("id", "10003")
                .log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"" + expectedMessage + "\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(session)
                .when()
                .post("/rest/api/2/issue/{id}/comment")
                .then()
                .log().all()
                .assertThat().statusCode(201);

        // Add attachment
        String addCommentResponse = given().header("X-Atlassian-Token", "no-check")
                .filter(session)
                .pathParam("id", "10003")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("src/main/resources/jira.txt"))
                .when()
                .post("/rest/api/2/issue/{id}/attachments")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.getString("id");

        // Get Issue
        String issueDetails = given().filter(session).pathParam("key", "10003").when().log().all()
                .queryParam("fields", "comment")
                .get("/rest/api/2/issue/{key}").then().log().all()
                .extract().response().asString();

        System.out.println(issueDetails);

        JsonPath js2 = new JsonPath(issueDetails);
        int commentsCount = js2.getInt("fields.comment.comments.size()");
        for(int i = 0; i < commentsCount; i++) {
            String commentIdIssue = js2.get("fields.comment.comments[" + i + "].id").toString();
            if(commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = js2.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(expectedMessage, message);
            }
        }
    }
}
