package api.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.order.OrderCreateJson;

import static io.restassured.RestAssured.with;

public class OrderApiClient {

    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Отправка запроса на создание списка POST /api/v1/orders")
    public Response createOrder(OrderCreateJson jsonBody) {
        return with()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/orders");
    }

    @Step("Отправка запроса на получение списка заказов GET /api/v1/orders")
    public Response getListOrders() {
        return with()
                .baseUri(BASE_URL)
                .get("/api/v1/orders");
    }
}
