import courierBody.CourierCreateJson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjY4NDM3MTA0NmM5ZDAwM2QxNmYwNjUiLCJpYXQiOjE2NTQ4ODYwNDksImV4cCI6MTY1NTQ5MDg0OX0.f7W1mVcW2vRffmbmCe-kLJUEkyD4sQT4rHyLzw0jrAY";
    CourierCreateJson createJson;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        createJson = new CourierCreateJson("testLoginSprint31", "1234", "eminem");
    }

    @Test
//    @DisplayName("Check status code of /users/me")
//    @Description("Описание статускоды")
    public void createCourierAccount() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createJson)
                        .post("/api/v1/courier");
        System.out.println(response.asString());


    }

}
