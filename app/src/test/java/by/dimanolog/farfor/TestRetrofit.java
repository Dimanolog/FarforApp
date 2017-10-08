package by.dimanolog.farfor;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import by.dimanolog.farfor.api.FarforApi;
import by.dimanolog.farfor.model.YmlCatalog;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Dimanolog on 13.01.2017.
 */

public class TestRetrofit {
    private static final int CONNECT_TIMEOUT = 1;
    private static final int READ_TIMEOUT = 1;
    private static final String BASE_URL = "http://ufa.farfor.ru/";
    private static String KEY = "ukAXxeJYZN";



    @Test
    public void testDownloadXmlAndParse() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient())
                .build();
        FarforApi farforApi = retrofit.create(FarforApi.class);

        YmlCatalog ymlCatalog;
        try {
            Response<YmlCatalog> response = farforApi.getData(KEY).execute();
            Assert.assertNotNull(response);
            ymlCatalog = response.body();
            Assert.assertNotNull(ymlCatalog);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .build();
        return client;
    }
}

