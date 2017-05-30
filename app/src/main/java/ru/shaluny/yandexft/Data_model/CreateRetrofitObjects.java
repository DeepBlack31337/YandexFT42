package ru.shaluny.yandexft.Data_model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by administrator on 29.05.2017.
 */

public class CreateRetrofitObjects {

    //////Две функции для создания обьектов retrofit
    /////можно обьединить в одну но не стоит

    ////переводчик
    public static TranInt crtRetrofitTrans() {
        //////////Строим запрос
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/") //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        /////Для плохой связи
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        TranInt tran = retrofit.create(TranInt.class); //Создаем объект, при помощи которого будем выполнять запросы
        return tran;
    }

    ////словарь
    public static DictInt crtRetrofitDict() {

        /////Для плохой связи
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //////////Строим запрос для словаря
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net/") //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        DictInt dict = retrofit.create(DictInt.class); //Создаем объект, при помощи которого будем выполнять запросы словаря
        return dict;
    }



}
