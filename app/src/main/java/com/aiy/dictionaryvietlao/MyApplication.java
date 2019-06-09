package com.aiy.dictionaryvietlao;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().assetFile("my_dictionary.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
