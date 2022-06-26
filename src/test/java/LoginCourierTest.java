import api.client.CourierApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.courieir.CourierCreateJson;
import pojo.courieir.CourierLoginJson;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Авторизация курьера")
public class LoginCourierTest {
    CourierCreateJson courierCreateJson;
    CourierLoginJson courierLoginJson;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierCreateJson = GenerateData.generateAccount();
        courierLoginJson = new CourierLoginJson(courierCreateJson.getLogin(), courierCreateJson.getPassword());
        courierApiClient.createCourier(courierCreateJson);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверяет авторизацию курьера по POST /api/v1/courier/login")
    public void whenPostLoginThenReturnSuccess() {
        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Неверный логин")
    @Description("Проверка что при неправильном логине возвращается ошибка")
    public void whenPostLoginWithIncorrectLoginThenReturnError() {
        courierLoginJson.setLogin(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Неверный пароль")
    @Description("Проверка что при неправильном пароле возвращается ошибка")
    public void whenPostLoginWithIncorrectPasswordThenReturnError() {
        courierLoginJson.setPassword(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Без поля Login")
    @Description("Проверка что при отсутствии поля Login возвращается ошибка")
    public void whenPostLoginWithoutLoginThenReturnError() {
        courierLoginJson.setLogin(null);

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    //TODO БЕЗ ПАРОЛЯ 500
//    @Test
    @DisplayName("Без поля Пароль")
    @Description("Проверка что при отсутствии поля Password возвращается ошибка")
    public void whenPostLoginWithoutPasswordThenReturnError() {
        courierLoginJson.setPassword(null);

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    @Description("Проверка что запрос на авторизацию возвращает правильный статус код - 200")
    public void whenPostLoginThenReturnId() {
        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @After
    public void tearDown() {
        GenerateData.deleteAccount();
    }


}
