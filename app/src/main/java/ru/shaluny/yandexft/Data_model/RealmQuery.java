package ru.shaluny.yandexft.Data_model;

import android.text.Html;

import java.util.Date;
import java.util.List;

import io.realm.Realm;

import static ru.shaluny.yandexft.Pres_model.GlobalVars.realm;

/**
 * Created by administrator on 29.05.2017.
 */

public class RealmQuery {

public static void InsertTransCache(CacheTable rctable, String text,String resptext,String langf) {

    rctable = new CacheTable(); //таблица рилм кеша
    rctable = new CacheTable();
    rctable.setFirst(text);
    rctable.setSecond(resptext.substring(1,resptext.length()-1));
    rctable.setFlow(langf);
    rctable.setUID();
    Date date = new Date();
    rctable.setDate(date.getTime());
    writeToRealm(rctable);
}

public static void InsertDictCache(CacheTable rctable, String txt, String ts, List trtext, String text, String resptext, String langf) {
    rctable.setFirst(text);
    rctable.setSecond(resptext);
    rctable.setFlow(langf);
    rctable.setUID();
    rctable.setAnn(Html.fromHtml("<font color=RED><b>" + txt + "   <i>" + ts + "</i></font></b><br>" + trtext.toString().substring(1,trtext.toString().length()-1)).toString());
    Date date = new Date();
    rctable.setDate(date.getTime());
    writeToRealm(rctable);
}


//////Поиск в Рилме кеша
    public static CacheTable CacheRead(String name, String flow) {
        return findInRealm(realm, name,flow);
    }

    //////Поиск в рилме языка
    public static LangTable LangRead(String name) {
        return findInRealmLang(realm, name);
    }

    public static CacheTable findInRealm(Realm realm, String name, String flow) {
        return realm.where(CacheTable.class).equalTo("UID", flow+name).findFirst();
    }

    public static LangTable findInRealmLang(Realm realm, String name) {
        return realm.where(LangTable.class).equalTo("lang", name).findFirst();
    }

    //////Запись в Рилм кеша
    public static String writeToRealm(CacheTable cacheTable) {
        //Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(transactionRealm -> {
            transactionRealm.copyToRealmOrUpdate(cacheTable);
        });
        return cacheTable.getFirst();
    }

    //////Запись в Рилм языков
    public static String writeToRealmLang(LangTable langTable) {
        //Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(transactionRealm -> {
            transactionRealm.copyToRealmOrUpdate(langTable);
        });
        return langTable.getLang();
    }

}
