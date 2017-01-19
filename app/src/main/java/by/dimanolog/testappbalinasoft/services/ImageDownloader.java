package by.dimanolog.testappbalinasoft.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;

import by.dimanolog.testappbalinasoft.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dimanolog on 17.01.2017.
 */

public class ImageDownloader {
    public interface ImageDownloadedCallback {
        public void onImageDowloaded(Bitmap bitmap);
    }
    private Context mContext;
    private static ImageDownloader sInstance;



    public static ImageDownloader getInstance(Context context){
        if(sInstance ==null) {
            sInstance = new ImageDownloader(context);
        }
        return sInstance;

    }

    private ImageDownloader(Context context){
        mContext=context.getApplicationContext();
    }

    public void downloadImage(String imageUrl, ImageDownloadedCallback callback) {

        new AsyncImageDownloader(callback).doInBackground(imageUrl);

    }


   private class AsyncImageDownloader extends AsyncTask<String, Void, Bitmap>{
       private ImageDownloadedCallback mCallback;

       public AsyncImageDownloader(ImageDownloadedCallback callback){
           mCallback=callback;
       }

       @Override
       protected Bitmap doInBackground(String... imageUrls) {
           String str=imageUrls[0];
           Call<ResponseBody> call = App.getDownloadImageService()
                   .downloadFileWithDynamicUrl(str);

           try {
               Response<ResponseBody> response = call.execute();
               InputStream inputStream = response.body().byteStream();
               final  Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               return bitmap;

           } catch (IOException e) {
               e.printStackTrace();
           }

           return null;
       }

       @Override
       protected void onPostExecute(Bitmap bitmap) {
            mCallback.onImageDowloaded(bitmap);
       }
   }

}
