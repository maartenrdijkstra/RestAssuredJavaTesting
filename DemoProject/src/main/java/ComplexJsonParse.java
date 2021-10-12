import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.coursePrice());

        // Print number of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);

        // Print purchase amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        // Print title of the first course
        String titleFirstCourse = js.getString("courses[0].title");
        System.out.println(titleFirstCourse);

        // Print all course titles and their respective prices
        for (int i = 0; i < count; i++) {

            String courseTitle = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");

            System.out.println(courseTitle + " - " + price);
        }

        // Print number of copies sold by RPA course

        System.out.println("Print number of copies sold by RPA course");
        for (int i = 0; i < count; i++) {

            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }

        // Verify if sum of all prices matches with purchaseAmount
        int sumOfAllPrices = 0;
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            sumOfAllPrices += price * copies;
        }

        Assert.assertEquals(sumOfAllPrices, totalAmount);

    }

}
