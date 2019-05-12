package com.anothay.dictionaryvietlao.model;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class WordDao {
    private Realm realm;
    public WordDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return realm;
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
