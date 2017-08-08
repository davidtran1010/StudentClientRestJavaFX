package sample.Model.DAO;

public class ApiUtils {

    public static final String BASE_URL = "http://ktpm.000webhostapp.com";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}