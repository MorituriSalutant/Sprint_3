import courier.CourierCreateJson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierTest {

    CourierCreateJson courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courier = new CourierCreateJson(
                "LoginTestApi" + new Random().nextInt(1000),
                "PasswordTestApi" + new Random().nextInt(1000),
                "FirstNameTestApi" + new Random().nextInt(1000));
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void createCourierTest() {
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cantCreateBothCourierTest() {
        //Отправили первый запрос на создание, он успешный
        postMethodCreateCourier();
        //Отправили второй запрос на создание, он ошибочный
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать обязательные Login и Password")
    public void ifAttributeLoginIsEmptyThenReturnError() {
        courier.setFirstName(null);
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void createCourierAccountTest() {
        Response response = postMethodCreateCourier();

        response.then().statusCode(201);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void createResponseReturnValidBody() {
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать login")
    public void requiredAttributeLoginTest() {
        courier.setLogin(null);
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать password")
    public void requiredAttributePasswordTest() {
        courier.setPassword(null);
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cantCreateCourierWithSameLoginTest() {
        //Отправили первый запрос на создание, он успешный
        postMethodCreateCourier();
        //Отправили второй запрос на создание, он ошибочный
        Response response = postMethodCreateCourier();

        response.then()
                .assertThat()
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @After
    public void tearDown() {
        System.out.println("Удалить аккаунт");
    }

    public Response postMethodCreateCourier() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post("/api/v1/courier");
    }
}
