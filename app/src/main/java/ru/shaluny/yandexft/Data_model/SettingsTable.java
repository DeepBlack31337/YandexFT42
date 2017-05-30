package ru.shaluny.yandexft.Data_model;

import io.realm.RealmObject;

/**
 * Created by administrator on 22.04.2017.
 */

public class SettingsTable extends RealmObject {
    private Boolean WorkOffline;

    public Boolean getWorkOffline() {
        return WorkOffline;
    }

    public void setWorkOffline(Boolean workOffline) {
        WorkOffline = workOffline;
    }
}
