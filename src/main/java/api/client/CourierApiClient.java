package api.client;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.courieir.CourierCreateJson;
import pojo.courieir.CourierLoginJson;

import static io.restassured.RestAssured.with;

public class CourierApiClient {

    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Отправка запроса на создание курьера POST /api/v1/courier | Логин = {jsonBody.login} Пароль = {jsonBody.password}")
    public Response createCourier(CourierCreateJson jsonBody) {
        return with()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier");
    }

    @Step("Отправка запроса на авторизацию курьера POST /api/v1/courier/login | Логин = {jsonBody.login} Пароль = {jsonBody.password}" )
    public Response loginCourier(CourierLoginJson jsonBody) {
        return with()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier/login");
    }

    @Step("Отправка запроса на удаление курьера DELETE /api/v1/courier/{id} ")
    public void deleteCourier(String id) {
        with()
                .baseUri(BASE_URL)
                .delete("/api/v1/courier/" + id);
    }
}
