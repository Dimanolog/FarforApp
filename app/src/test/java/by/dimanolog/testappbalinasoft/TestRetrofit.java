package by.dimanolog.testappbalinasoft;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import by.dimanolog.testappbalinasoft.api.UfaFarforApi;
import by.dimanolog.testappbalinasoft.beans.YmlCatalog;
import by.dimanolog.testappbalinasoft.services.DownloadImageService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    private static String IMAGE_URL = "http://ufa.farfor.ru/media/menu/products/%D0%A1%D1%83%D1%81%D0%B8_%D1%81_%D0%BB%D0%BE%D1%81%D0%BE%D1%81%D0%B5%D0%BC.jpg";

    @Test
    public void testDownloadXmlAndParse() {

        /*Strategy strategy=new CycleStrategy("id","categoryId", "category");
        Serializer serializer = new Persister(strategy);*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient())
                .build();
        UfaFarforApi ufaFarforApi = retrofit.create(UfaFarforApi.class);

        /*ufaFarforApi.getData(KEY)
                .enqueue(new Callback<Shop>() {
                    @Override
                    public void onResponse(Call<Shop> call, Response<Shop> response) {
                        Assert.assertNotNull(response);
                        Shop shop=response.body();
                        Assert.assertNotNull(shop);

                    }

                    @Override
                    public void onFailure(Call<Shop> call, Throwable t) {

                    }
                });*/
        YmlCatalog ymlCatalog;
        try {
            Response<YmlCatalog> response = ufaFarforApi.getData(KEY).execute();
            Assert.assertNotNull(response);
            ymlCatalog = response.body();
            Assert.assertNotNull(ymlCatalog);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadImage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .build();

        DownloadImageService downloadImageService = retrofit.create(DownloadImageService.class);

        Call<ResponseBody> call = downloadImageService.downloadFileWithDynamicUrl(IMAGE_URL);

        try {
            Response<ResponseBody> response = call.execute();
            InputStream inputStream = response.body().byteStream();

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

