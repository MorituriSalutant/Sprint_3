import api.client.CourierApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.courieir.CourierCreateJson;
import pojo.courieir.CourierLoginJson;

public class GenerateData {

    private static String login;
    private static String password;
    private static String firstName;

    //Создать json для создания курьера - Логин, Пароль, Имя
    @Step("Генерация и создание JSON с логином , паролем {GenerateData.password}, именем {GenerateData.firstName}")
    public static CourierCreateJson generateAccount() {
        login = RandomStringUtils.random(15, true, true);
        password = RandomStringUtils.random(15, true, true);
        firstName = RandomStringUtils.random(15, true, true);

        return new CourierCreateJson(login, password, firstName);
    }

    //Удалить аккаунт, если он был создан
    @Step("Удаление аккаунта, если он был создан")
    public static void deleteAccount() {
        try {
            CourierApiClient courierApiClient = new CourierApiClient();
            CourierLoginJson courierLoginJson = new CourierLoginJson(login, password);
            Response response = courierApiClient.loginCourier(courierLoginJson);
            String id = response.then().extract().path("id").toString();
            courierApiClient.deleteCourier(id);
        } catch (NullPointerException e) {
            System.out.println("Удаление невозможно, аккаунт не создан");
        }
    }
}
