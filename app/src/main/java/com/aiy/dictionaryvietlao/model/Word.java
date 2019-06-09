package com.aiy.dictionaryvietlao.model;

import io.realm.RealmObject;

public class Word extends RealmObject {
    private String lao;
    private String viet;

    public String getLao() {
        return lao;
    }

    public String getViet() {
        return viet;
    }

    public void setLao(String lao) {
        this.lao = lao;
    }

    public void setViet(String viet) {
        this.viet = viet;
    }
}
