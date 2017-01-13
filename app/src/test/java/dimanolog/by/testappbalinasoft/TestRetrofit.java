package dimanolog.by.testappbalinasoft;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import dimanolog.by.testappbalinasoft.api.UfaFarforApi;
import dimanolog.by.testappbalinasoft.beans.YmlCatalog;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Dimanolog on 13.01.2017.
 */

public class TestRetrofit {
    public static final String BASE_URL = "http://ufa.farfor.ru/";
    private static String KEY="ukAXxeJYZN";
    @Test
    public void test()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
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
            Response<YmlCatalog> response=ufaFarforApi.getData(KEY).execute();
            Assert.assertNotNull(response);
            ymlCatalog=response.body();
            Assert.assertNotNull(ymlCatalog);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
