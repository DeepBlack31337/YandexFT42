package ru.shaluny.yandexft.Pres_model;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;
import ru.shaluny.yandexft.Data_model.PostModel;

import static ru.shaluny.yandexft.Data_model.InetQuery.TranslateQuery;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.key;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang1;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang2;


/**
 * Created by administrator on 22.04.2017.
 */

public class Connectivity {
    public static String resptxt="";

    ////Запрос на слово в Яндекс
    public static String RetroGetText(String text) {

        TranslateQuery(key, text, lang1+"-"+lang2)
            .subscribe(new DisposableObserver<PostModel>() {

        @Override
        public void onNext(PostModel value) {
            Log.d("GET_RESPONSE", value.getText().toString());
            //SetText(value.getText().toString());
            resptxt = value.getText().toString();
            //txt2.setText(value.getText().toString());
        }

        @Override
        public void onError(Throwable e) {
            Log.d("GET_ERROR", e.getMessage());

        }

        @Override
        public void onComplete() {

        }

    });

        return resptxt;

    }
}
