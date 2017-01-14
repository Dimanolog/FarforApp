package by.dimanolog.testappbalinasoft;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import by.dimanolog.testappbalinasoft.api.UfaFarforApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Dimanolog on 13.01.2017.
 */

public class App extends Application {
    private static final int CONNECT_TIMEOUT = 1;
    private static final int READ_TIMEOUT = 1;
    private static final String HTTP_UFA_FARFOR_RU = "http://ufa.farfor.ru/";
    private static String KEY="ukAXxeJYZN";

    private UfaFarforApi mUfaFarforApi;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

       // Strategy strategy = new CycleStrategy("id", "categoryId");
        //Serializer serializer=new Persister(strategy);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HTTP_UFA_FARFOR_RU) //Базовая часть адреса
                .addConverterFactory(SimpleXmlConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .client(getClient())
                .build();
        mUfaFarforApi = mRetrofit.create(UfaFarforApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .build();
        return client;
    }

    public  UfaFarforApi getApi() {
        return mUfaFarforApi;
    }
}
