package ru.shaluny.yandexft.Data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 24.04.2017.
 */

public class LangModel {
    @SerializedName("dirs")
    @Expose
    private List<String> dirs = null;
    @SerializedName("langs")
    @Expose
    private Object langs;

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public Object getLangs() {
        return langs;
    }

    public void setLangs(Object langs) {
        this.langs = langs;
    }
}
