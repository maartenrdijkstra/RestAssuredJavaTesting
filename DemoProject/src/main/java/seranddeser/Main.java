package seranddeser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;

public class Main {

    public static void main(String[] args) throws IOException {
        String payload = FileUtils.readFileToString(new File("src/main/resources/payload.json"),
                Charset.defaultCharset());
//        System.out.println(payload);

        InputStream in;
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/payload.json"));
        GetCourse getCourse = new Gson().fromJson(br, GetCourse.class);
//        System.out.println(getCourse.getCourses().getApi().get(0).getCourseTitle());

    }
}
