package by.dimanolog.farfor.api;

import by.dimanolog.farfor.model.YmlCatalog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dimanolog on 13.01.2017.
 */

public interface FarforApi
{       @GET("/getyml")
        Call<YmlCatalog>getData(
                @Query("key")String key);
}
