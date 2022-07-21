package api.client;

import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class RestAssuredClient {
    protected final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    protected Filter res = new ResponseLoggingFilter();
    protected Filter req = new RequestLoggingFilter();
    protected RequestSpecification reqSpec = with()
            .filters(req, res)
            .baseUri(BASE_URL);

}
