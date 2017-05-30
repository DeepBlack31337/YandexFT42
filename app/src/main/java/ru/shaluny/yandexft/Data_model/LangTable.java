package ru.shaluny.yandexft.Data_model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by administrator on 24.04.2017.
 */

public class LangTable extends RealmObject {
    @PrimaryKey
    private String lang;
    private String langm;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLangm() {
        return langm;
    }

    public void setLangm(String langm) {
        this.langm = langm;
    }
}
