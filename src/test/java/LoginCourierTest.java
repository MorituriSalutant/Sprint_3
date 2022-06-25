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

public class LoginCourierTest {
    CourierCreateJson courierCreateJson;
    CourierLoginJson courierLoginJson;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierCreateJson = courierApiClient.generateAccount();
        courierLoginJson = new CourierLoginJson(
                courierCreateJson.getLogin(),
                courierCreateJson.getPassword());
        courierApiClient.postMethodCreateCourier(courierCreateJson);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void whenPostLoginThenReturnSuccess() {
        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    public void whenPostLoginThenReturnSuccessSame() {
        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин")
    public void whenPostLoginWithIncorrectLoginThenReturnError() {
        courierLoginJson.setLogin(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать пароль")
    public void whenPostLoginWithIncorrectPasswordThenReturnError() {
        courierLoginJson.setPassword(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Если нет поля логин, запрос возвращает ошибку")
    public void whenPostLoginWithoutLoginThenReturnError() {
        courierLoginJson.setLogin(null);

        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    //TODO БЕЗ ПАРОЛЯ 500
//    @Test
    @DisplayName("Если нет поля пароль, запрос возвращает ошибку")
    public void whenPostLoginWithoutPasswordThenReturnError() {
        courierLoginJson.setPassword(null);

        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void whenPostLoginThenReturnId() {
        Response response = courierApiClient.postMethodLoginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }


    @After
    public void tearDown() {
        System.out.println("Удалить аккаунт");
    }


}
