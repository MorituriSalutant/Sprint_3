import courier.CourierCreateJson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Курьера можно создать")
    public void createCourierTest() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(rndLogin, rndPassword, rndFirstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cantCreateBothCourierTest() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(rndLogin, rndPassword, rndFirstName);

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

        createSecondCourierResponse.then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать обязательные Login и Password")
    public void ifAttributeLoginIsEmptyThenReturnError() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);

        CourierCreateJson courier = new CourierCreateJson();
        courier.setLogin(rndLogin);
        courier.setPassword(rndPassword);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void createCourierAccountTest() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(rndLogin, rndPassword, rndFirstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then().statusCode(201);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void createResponseReturnValidBody() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(rndLogin, rndPassword, rndFirstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }


    @Test
    @DisplayName("Чтобы создать курьера, нужно передать login")
    public void requiredAttributeLoginTest() {

        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson();
        courier.setPassword(rndPassword);
        courier.setFirstName(rndFirstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать password")
    public void requiredAttributePasswordTest() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);

        CourierCreateJson courier = new CourierCreateJson();
        courier.setLogin(rndLogin);
        courier.setFirstName(rndFirstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post("/api/v1/courier");

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cantCreateCourierWithSameLoginTest() {
        String rndLogin = RandomStringUtils.random(15, true, true);
        String rndPassword = RandomStringUtils.random(15, true, true);
        String rndFirstName = RandomStringUtils.random(15, true, false);
        CourierCreateJson courier = new CourierCreateJson(rndLogin, rndPassword, rndFirstName);

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

        createSecondCourierResponse.then()
                .assertThat()
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }


}
