import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumOfCourses() {
        JsonPath js = new JsonPath(Payload.coursePrice());

        int count = js.getInt("courses.size()");

        int sumAmount = 0;
        for (int i = 0; i < count; i++) {
            int price =  js.getInt("courses[" + i + "].price");
            int copies =  js.getInt("courses[" + i + "].copies");
            sumAmount += price * copies;
        }

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount, sumAmount);
    }
}
