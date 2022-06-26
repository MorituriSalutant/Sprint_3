import api.client.OrderApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@Feature("Получение заказов")
public class OrdersGetListTest {
    OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Возвращается список заказов")
    @Description("Проверяет что возвращается список заказов с полями")
    public void whenGetOrdersListThenReturnValid() {
        Response response = orderApiClient.getListOrders();

        response.then()
                .assertThat()
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("availableStations", notNullValue())
                .statusCode(200);
    }
}