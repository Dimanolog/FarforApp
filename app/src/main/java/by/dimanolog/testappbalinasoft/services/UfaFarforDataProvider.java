package by.dimanolog.testappbalinasoft.services;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.dimanolog.testappbalinasoft.App;
import by.dimanolog.testappbalinasoft.beans.Category;
import by.dimanolog.testappbalinasoft.beans.Offer;
import by.dimanolog.testappbalinasoft.beans.YmlCatalog;
import retrofit2.Response;

/**
 * Created by Dimanolog on 16.01.2017.
 */

public class UfaFarforDataProvider {
    private static final String TAG=UfaFarforDataProvider.class.getSimpleName();
    private static UfaFarforDataProvider sInstance;
    private YmlCatalog mYmlCatalog;
    private Context mContext;

    public static UfaFarforDataProvider getInstance(Context context){
        if(sInstance==null){
            sInstance=new UfaFarforDataProvider(context);
        }
        return sInstance;

    }

    private UfaFarforDataProvider(Context context){
        mContext=context.getApplicationContext();
    }

    public boolean isReady(){
        return mYmlCatalog!=null;

    }

    public void update()
    {
        try {
            getDataFromHttp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Category>  getCategorysList(){
        List<Category> categoryList=new ArrayList<>();
        if(mYmlCatalog!=null) {
            categoryList.addAll( mYmlCatalog.getShop().getCategories());
        }
        return categoryList;
    }


    public List<Offer> getOffers(){
        List<Offer> offersList=new ArrayList<>();
        if(mYmlCatalog!=null) {
           offersList.addAll(mYmlCatalog.getShop().getOffers());
        }
        return offersList;
    }


    public List<Offer> getOfferInCategory(@Nullable Category category){
        if(category==null){
            return getOffers();
        }
        Long categoryId=category.getId();
        List<Offer>offersInCategory=new ArrayList<>();
        List<Offer>offerList=mYmlCatalog.getShop().getOffers();
        for (Offer offer:offerList){
            if(offer.getCategoryId().equals(categoryId)){
                offersInCategory.add(offer);
            }
        }

        return offersInCategory;
    }
    private boolean getDataFromHttp() throws IOException {
        Response<YmlCatalog> response=App.getFarforApi()
                .getData(App.getKey())
                .execute();
        if(response.isSuccessful()){
          mYmlCatalog=response.body();
            return true;
        }
        return false;
    }



    private void getDataFromDb(){


    }


}
