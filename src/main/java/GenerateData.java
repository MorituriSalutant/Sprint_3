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

    @Step("Создание JSON Логин, Пароль, Имя")
    public static CourierCreateJson generateAccount() {
        createAccountData();
        return new CourierCreateJson(login, password, firstName);
    }

    @Step("Генерация логина и пароля")
    public static void createAccountData(){
        login = RandomStringUtils.random(15, true, true);
        password = RandomStringUtils.random(15, true, true);
        firstName = RandomStringUtils.random(15, true, true);
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
