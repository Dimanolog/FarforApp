package by.dimanolog.testappbalinasoft.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import by.dimanolog.testappbalinasoft.App;
import by.dimanolog.testappbalinasoft.beans.Category;
import by.dimanolog.testappbalinasoft.beans.Offer;
import by.dimanolog.testappbalinasoft.beans.YmlCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dimanolog on 16.01.2017.
 */

public class UfaFarforDataProvider {
    private static final String TAG=UfaFarforDataProvider.class.getSimpleName();
    private static UfaFarforDataProvider sUfaFarforDataProvider;
    private YmlCatalog mYmlCatalog;
    private Context mContext;

    public static UfaFarforDataProvider getInstance(Context context){
        if(sUfaFarforDataProvider!=null)
        {
            return sUfaFarforDataProvider;
        }else {
            return new UfaFarforDataProvider(context);
        }
    }

    private UfaFarforDataProvider(Context context){
        mContext=context;
        UfaFarforDataProviderInit();

    }


    public List<Category>  getCategorysList(){
        if(mYmlCatalog!=null) {
            return mYmlCatalog.getShop().getCategories();
        }
        return null;
    }

    public List<Offer> getOffers(){
        if(mYmlCatalog!=null) {
            return mYmlCatalog.getShop().getOffers();
        }
        return null;
    }

    private void getDataFromHttp(){
        App.getUfaFarforApi()
                .getData(App.getKey())
                .enqueue(new Callback<YmlCatalog>() {
                    @Override
                    public void onResponse(Call<YmlCatalog> call, Response<YmlCatalog> response) {
                        if(response.isSuccessful()) {
                            mYmlCatalog = response.body();
                        }else {
                            Log.w(TAG,"cant acces to resource");
                        }
                    }
                    @Override
                    public void onFailure(Call<YmlCatalog> call, Throwable t) {
                            Log.e(TAG,"Failure",t);
                    }
                });
    }
    private void UfaFarforDataProviderInit()
    {


    }

    private void getDataFromDb(){

    }


}
