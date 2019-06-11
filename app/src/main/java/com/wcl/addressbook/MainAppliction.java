package com.wcl.addressbook;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class MainAppliction extends Application {


    private static MainAppliction instance;
    private DaoSession mDaoSession;


    public static synchronized MainAppliction getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "address.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
