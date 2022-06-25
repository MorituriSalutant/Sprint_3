package pojo.courieir;

public class CourierLoginJson {
    private String login;
    private String password;
    private long id;

    public CourierLoginJson() {
    }

    public CourierLoginJson(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
