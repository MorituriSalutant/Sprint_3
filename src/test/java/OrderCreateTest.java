import api.client.OrderApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.order.OrderCreateJson;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;
    OrderApiClient orderApiClient;
    OrderCreateJson orderCreateJson;

    public OrderCreateTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
        orderCreateJson = new OrderCreateJson(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                color
        );
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {
                        "Alexanrd",
                        "Chepukhov",
                        "ул. Пушкина д.Колотушкина",
                        4,
                        "+7 906 90 64 321",
                        5,
                        "2020-06-06",
                        "comment",
                        List.of("BLACK")},
                {
                        "Oleg",
                        "Chepukhov",
                        "ул. Пушкина д.Колотушкина",
                        4,
                        "+7 906 90 64 321",
                        5,
                        "2020-06-06",
                        "comment",
                        List.of("GREY")},
                {
                        "Katya",
                        "Chepukhov",
                        "ул. Пушкина д.Колотушкина",
                        4,
                        "+7 906 90 64 321",
                        5,
                        "2020-06-06",
                        "comment",
                        List.of("GREY", "BLACK")},
                {
                        "Dima",
                        "Malakhov",
                        "ул. Пушкина д.Колотушкина",
                        4,
                        "+7 906 90 64 321",
                        5,
                        "2020-06-06",
                        "comment",
                        null}
        };
    }

    @Test
    @DisplayName("Тело ответа содержит track")
    public void whenPostOrderThenReturnTrack() {
        Response response = orderApiClient.createOrder(orderCreateJson);

        response.then()
                .assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
