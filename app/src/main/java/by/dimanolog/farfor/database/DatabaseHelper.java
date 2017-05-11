package by.dimanolog.farfor.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import by.dimanolog.farfor.model.Category;
import by.dimanolog.farfor.model.Offer;
import by.dimanolog.farfor.model.Param;

/**
 * Created by Dimanolog on 24.01.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "farfor.db";
    private static final int DATABASE_VERSION = 6;

    private RuntimeExceptionDao<Category, Long> mCategoryDao;
    private RuntimeExceptionDao<Offer, Long> mOfferDao;
    private RuntimeExceptionDao<Param,Long>mParamDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            Log.i(TAG, "onCreate");
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Param.class);
            TableUtils.createTable(connectionSource, Offer.class);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "onUpgrade");
            TableUtils.dropTable(connectionSource, Param.class, true);
            TableUtils.dropTable(connectionSource, Offer.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao<Category, Long> getCategoryDao() {
        if (mCategoryDao == null) {
            mCategoryDao = getRuntimeExceptionDao(Category.class);
        }
        return mCategoryDao;
    }

    public RuntimeExceptionDao<Offer, Long> getOfferDao() {
        if (mOfferDao == null) {
            mOfferDao = getRuntimeExceptionDao(Offer.class);
        }
        return mOfferDao;
    }
    public RuntimeExceptionDao<Param, Long> getParamDao() {
        if (mParamDao == null) {
            mParamDao = getRuntimeExceptionDao(Param.class);
        }
        return mParamDao;
    }


    public void clearDatabase() {
        try {
            TableUtils.clearTable(getConnectionSource(), Param.class);
            TableUtils.clearTable(getConnectionSource(), Offer.class);
            TableUtils.clearTable(getConnectionSource(), Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "cant clear database");
        }

    }

    @Override
    public void close() {
        super.close();
        mOfferDao = null;
        mCategoryDao = null;
    }

}
