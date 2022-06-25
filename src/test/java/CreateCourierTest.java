import api.client.CourierApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.courieir.CourierCreateJson;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierTest {

    CourierCreateJson courierCreateJson;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierCreateJson = courierApiClient.generateAccount();
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void whenPostCreateThenReturnSuccess() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void whenIdenticallyPostCreateThenReturnError() {
        //Отправили первый запрос на создание, он успешный
        courierApiClient.createCourier(courierCreateJson);
        //Отправили второй запрос на создание, он ошибочный
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать обязательные Login и Password")
    public void whenPostCreateWithoutNameThenReturnValidBody() {
        courierCreateJson.setFirstName(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void whenPostCreateThenReturnValidStatusCode() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then().statusCode(201);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void whenPostCreateThenReturnValidBody() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать login")
    public void whenPostCreateWithoutLoginThenReturnError() {
        courierCreateJson.setLogin(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать password")
    public void whenPostCreateWithoutPasswordThenReturnError() {
        courierCreateJson.setPassword(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void whenIdenticallyLoginPostCreateThenReturnError() {
        //Отправили первый запрос на создание, он успешный
        courierApiClient.createCourier(courierCreateJson);
        //Отправили второй запрос на создание, он ошибочный
        Response response = courierApiClient.createCourier(courierCreateJson);

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

}
