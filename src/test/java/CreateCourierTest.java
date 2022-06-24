import courier.CourierCreateJson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void createCourierAccountTest() {
        String generatedLogin = RandomStringUtils.random(15, true, true);
        String generatedPassword = RandomStringUtils.random(15, true, true);
        String generatedName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(generatedLogin, generatedPassword, generatedName);

        Response createCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        createCourierResponse.then().statusCode(201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cantCreateBothCourierTest(){
        String generatedLogin = RandomStringUtils.random(15, true, true);
        String generatedPassword = RandomStringUtils.random(15, true, true);
        String generatedName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(generatedLogin, generatedPassword, generatedName);

        Response createFirstCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        Response createSecondCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        createSecondCourierResponse.then().statusCode(409);
    }


}
