package by.dimanolog.testappbalinasoft.services;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import by.dimanolog.testappbalinasoft.App;
import by.dimanolog.testappbalinasoft.R;
import by.dimanolog.testappbalinasoft.database.DatabaseHelper;
import by.dimanolog.testappbalinasoft.model.Category;
import by.dimanolog.testappbalinasoft.model.Offer;
import by.dimanolog.testappbalinasoft.model.Param;
import by.dimanolog.testappbalinasoft.model.Shop;
import by.dimanolog.testappbalinasoft.model.YmlCatalog;
import by.dimanolog.testappbalinasoft.utilities.Network;
import retrofit2.Response;

/**
 * Created by Dimanolog on 16.01.2017.
 */

public class FarforDataProvider {
    private static final String TAG = FarforDataProvider.class.getSimpleName();
    private static FarforDataProvider sInstance;

    private YmlCatalog mYmlCatalog;
    private Context mContext;

    private FarforDataProviderCallback mCallback;
    private DataLoadTask mLoadTask;
    private boolean mLoadingFlag;


    public interface FarforDataProviderCallback {

        void onStartLoad();
        void onFinishLoad();

    }


    public static FarforDataProvider getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FarforDataProvider(context);
        }
        return sInstance;

    }

    private FarforDataProvider(Context context) {

        mContext = context.getApplicationContext();

    }

    public void setCallback(FarforDataProviderCallback callback) {
        mCallback = callback;
    }

    public boolean isReady() {
        return mYmlCatalog != null;

    }

    public boolean isLoading() {
        return mLoadingFlag;
    }

    public void update() {
        if (!isLoading()) {
            mLoadTask = new DataLoadTask();
            mLoadTask.execute();

        }
    }

    public List<Category> getCategorysList() {
        List<Category> categoryList = new ArrayList<>();
        if (mYmlCatalog != null) {
            categoryList.addAll(mYmlCatalog.getShop().getCategories());
        }
        return categoryList;
    }


    public List<Offer> getOffers() {
        List<Offer> offersList = new ArrayList<>();
        if (mYmlCatalog != null) {
            offersList.addAll(mYmlCatalog.getShop().getOffers());
        }
        return offersList;
    }


    public List<Offer> getOfferInCategory(@Nullable Category category) {
        if (category == null) {
            return getOffers();
        }
        Long categoryId = category.getId();
        List<Offer> offersInCategory = new ArrayList<>();
        List<Offer> offerList = mYmlCatalog.getShop().getOffers();
        for (Offer offer : offerList) {
            if (offer.getCategoryId().equals(categoryId)) {
                offersInCategory.add(offer);
            }
        }

        return offersInCategory;
    }

    private YmlCatalog getData() {
        boolean connection = Network.checkConnection(mContext);
        if (connection) {
            try {
                YmlCatalog ymlCatalog = getDataFromHttp();
                addToDb(ymlCatalog);
                return ymlCatalog;
            } catch (IOException e) {
                Log.e(TAG, "cant access the server", e);
                return getDataNoConnection();
            }
        } else {
            return getDataNoConnection();
        }
    }

    private YmlCatalog getDataNoConnection() {
        if(!checkDbEmpty()) {
            return getDataFromDb();
        }else {
            return getDataFromXml();
        }
    }


    private YmlCatalog getDataFromHttp() throws IOException {
        Response<YmlCatalog> response = App.getFarforApi()
                .getData(App.getKey())
                .execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        return null;
    }


    private void addToDb(YmlCatalog ymlCatalog) {
        DatabaseHelper databaseHelper = App.getDatabaseHelper();
        databaseHelper.clearDatabase();

        List<Category> categoryList = ymlCatalog.getShop().getCategories();
        List<Offer> offerList = ymlCatalog.getShop().getOffers();
        databaseHelper.getCategoryDao().create(categoryList);
        databaseHelper.getOfferDao().create(offerList);
        RuntimeExceptionDao<Param, Long> paramDao = databaseHelper.getParamDao();
        for (Offer offer : offerList) {
            Collection<Param> params = offer.getParamCollection();
            if (params != null) {
                paramDao.create(params);
            }
        }
    }


    private YmlCatalog getDataFromDb() {

        DatabaseHelper databaseHelper = App.getDatabaseHelper();
        List<Category> categoryList = databaseHelper.getCategoryDao().queryForAll();
        List<Offer> offerList = databaseHelper.getOfferDao().queryForAll();
        Shop shop = new Shop();
        shop.setOffers(offerList);
        shop.setCategories(categoryList);
        YmlCatalog ymlCatalog = new YmlCatalog();
        ymlCatalog.setShop(shop);

        return ymlCatalog;

    }

    private YmlCatalog getDataFromXml(){
        InputStream istream = mContext.getResources().openRawResource(R.raw.farfor);
        Serializer serializer = new Persister();
        YmlCatalog ymlCatalog=null;
        try {
            ymlCatalog = serializer.read(YmlCatalog.class, istream);
            return ymlCatalog;
        } catch (Exception e) {
            Log.e(TAG,"cant read xml",e);

        }
        return ymlCatalog;

    }

    private boolean checkDbEmpty(){
        DatabaseHelper databaseHelper = App.getDatabaseHelper();
        long categoryCount=databaseHelper.getCategoryDao().countOf();
        long offerCount=databaseHelper.getOfferDao().countOf();
        if(categoryCount<1||offerCount<1){
            return true;
        }
        return false;
    }

    private class DataLoadTask extends AsyncTask<Void, Void, YmlCatalog> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingFlag = true;
            mCallback.onStartLoad();
        }

        @Override
        protected YmlCatalog doInBackground(Void... params) {
            return getData();
        }

        @Override
        protected void onPostExecute(YmlCatalog ymlCatalog) {

            mLoadingFlag = false;
            mYmlCatalog = ymlCatalog;
            mCallback.onFinishLoad();

        }
    }
}
