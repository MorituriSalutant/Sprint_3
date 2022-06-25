import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.courieir.CourierCreateJson;

import static io.restassured.RestAssured.with;

public class CourierApiClient {

    public Response postMethodCreateCourier(CourierCreateJson body) {
        return with()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/v1/courier");
    }
}
