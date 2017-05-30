package ru.shaluny.yandexft.Data_model;

import android.text.Spanned;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by administrator on 22.04.2017.
 */

public class CacheTable extends RealmObject {
    @PrimaryKey
    private String UID;
    private String flow;
    private String first;
    private String second;
    private String ann;
    private Long date;
    private Boolean izbr;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getAnn() {
        return ann;
    }

    public void setAnn(String ann) {
        this.ann = ann;
    }

    public Boolean getIzbr() {
        return izbr;
    }

    public void setIzbr(Boolean izbr) {
        this.izbr = izbr;
    }

    public String getUID() {
        return UID;
    }

    public void setUID() {
        this.UID = getFlow()+getFirst();
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
