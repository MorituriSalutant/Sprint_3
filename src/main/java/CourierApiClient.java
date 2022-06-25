import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.courieir.CourierCreateJson;
import pojo.courieir.CourierLoginJson;

import static io.restassured.RestAssured.with;

public class CourierApiClient {

    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public Response postMethodCreateCourier(CourierCreateJson jsonBody) {
        return with()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier");
    }

    public Response postMethodLoginCourier(CourierLoginJson jsonBody) {
        return with()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier/login");
    }
}
