package ru.shaluny.yandexft.Pres_model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Retrofit;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.Data_model.TranInt;

/**
 * Created by administrator on 22.04.2017.
 */

public class GlobalVars {
    public static Realm realm;
    public static String key = "trnsl.1.1.20170414T165202Z.442e7085636de5d9.556566446301a65e849da51fdb55a14e7ed681e9";
    public static String keydict = "dict.1.1.20170422T232737Z.532c7a11f562e36b.f6b2c1307d3bcc0c50c17d948d8dbfb78b0beb66";

    public static List<String> imLangM = new ArrayList<>();
    public static List<String> imLang = new ArrayList<>();
    public static String lang1;
    public static String lang2;

}
