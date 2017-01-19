package by.dimanolog.testappbalinasoft.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Dimanolog on 14.01.2017.
 */

public interface DownloadImageService {
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrl(
            @Url String fileUrl);
}
