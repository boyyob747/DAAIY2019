package com.aiy.dictionaryvietlao.model;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LocalDAO {
    private Context context;
    Realm realm;
    private boolean isFavorite;
    public static final String KEY = "VIETLAO";
    public LocalDAO(Context context,boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("myrealm.realm")
                    .schemaVersion(42)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            this.realm = Realm.getInstance(config);
        } else {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("myrealm2.realm")
                    .schemaVersion(33)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            this.realm = Realm.getInstance(config);
        }

    }
    public void setFavorite(Word word) {
        this.realm.beginTransaction();
        this.realm.copyToRealm(word);
        this.realm.commitTransaction();
    }
    public boolean checkIsFavorite (Word word) {
       return realm.where(Word.class).equalTo("viet",word.getViet()).findFirst() != null;
    }
    public void setHistory(Word word) {
        this.realm.beginTransaction();
        this.realm.copyToRealm(word);
        this.realm.commitTransaction();
    }
    public void deleteFavorite(Word word) {
        this.realm.beginTransaction();
        realm.where(Word.class).equalTo("viet",word.getViet()).findFirst().deleteFromRealm();
        this.realm.commitTransaction();
    }
    public void deleteHistory(Word word) {
        this.realm.beginTransaction();
        realm.where(Word.class).equalTo("viet", word.getViet()).findFirst().deleteFromRealm();
        this.realm.commitTransaction();
    }
    public RealmResults<Word> getFavorite(boolean isVietLao) {
        return realm.where(Word.class).findAll().sort(isVietLao ? "viet" : "lao");
    }
    public RealmResults<Word> getHistory(boolean isVietLao) {
        return realm.where(Word.class).findAll().sort(isVietLao ? "viet" : "lao");
    }
    public RealmResults<Word> getAllWords() {
        return realm.where(Word.class).findAll().sort("viet");
    }
    public RealmResults<Word> getLaoViet() {
        return realm.where(Word.class).findAll().sort("lao");
    }
    public RealmResults<Word> searchWord (String key, String keyWord) {
        return realm.where(Word.class).like(key, "*" + keyWord + "*").findAll();
    }
    public Word findWord (String key, String keyWord) {
        return realm.where(Word.class).like(key , keyWord ).findFirst();
    }
}
