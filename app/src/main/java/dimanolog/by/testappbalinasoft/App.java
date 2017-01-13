package dimanolog.by.testappbalinasoft;

import android.app.Application;

import dimanolog.by.testappbalinasoft.api.UfaFarforApi;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Dimanolog on 13.01.2017.
 */

public class App extends Application {
    private UfaFarforApi mUfaFarforApi;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

       // Strategy strategy = new CycleStrategy("id", "categoryId");
        //Serializer serializer=new Persister(strategy);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://ufa.farfor.ru/") //Базовая часть адреса
                .addConverterFactory(SimpleXmlConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        mUfaFarforApi = mRetrofit.create(UfaFarforApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public  UfaFarforApi getApi() {
        return mUfaFarforApi;
    }
}
