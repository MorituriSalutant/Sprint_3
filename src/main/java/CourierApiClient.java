import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
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

    //TODO Удалить аккаунты
//    public Response deleteMethodDeleteCourier(CourierLoginJson jsonBody){
//        return with()
//                .baseUri(BASE_URL)
//                .queryParam(Long.toString(jsonBody.getId()))
//                .delete("/api/v1/courier/");
//    }

    public CourierCreateJson generateAccount() {
        return new CourierCreateJson(
                RandomStringUtils.random(15, true, true),
                RandomStringUtils.random(15, true, true),
                RandomStringUtils.random(15, true, true));
    }
}
