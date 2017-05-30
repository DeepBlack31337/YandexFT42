package ru.shaluny.yandexft.Data_model;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.shaluny.yandexft.Data_model.DictModel.DictPostModel;
import ru.shaluny.yandexft.Data_model.PostModel;
import ru.shaluny.yandexft.Data_model.TranInt;

import static ru.shaluny.yandexft.Data_model.CreateRetrofitObjects.crtRetrofitDict;
import static ru.shaluny.yandexft.Data_model.CreateRetrofitObjects.crtRetrofitTrans;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.keydict;

/**
 * Created by administrator on 29.05.2017.
 */

public class InetQuery {

    public static Observable<PostModel> TranslateQuery(String key, String text, String langf) {
        //Если onError, повторяем 3 раза
        final Observable<PostModel> TranObs = crtRetrofitTrans().getData(key, text, langf).timeout(5, TimeUnit.SECONDS).retry(3)
                //.concatWith(v -> tranD.getAnn(keydict,text,"en-ru"))
                //.doOnNext(v -> prgrs2.setVisibility(ProgressBar.VISIBLE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return TranObs;
    }

    public static Observable<DictPostModel> DictionaryQuery(String langf, String text) {
        final Observable<DictPostModel> DictObs = crtRetrofitDict().getAnn(keydict, langf, text)
                .subscribeOn(Schedulers.io()).timeout(5, TimeUnit.SECONDS).retry(3)
                .observeOn(AndroidSchedulers.mainThread());
        return DictObs;
    }

    public static Observable<LangModel> LangsQuery(String key) {
        final Observable<LangModel> LangObs = crtRetrofitTrans().getLangs(key, "ru").timeout(5, TimeUnit.SECONDS).retry(3)
                //.concatWith(v -> tranD.getAnn(keydict,text,"en-ru"))
                //.doOnNext(v -> prgrs2.setVisibility(ProgressBar.VISIBLE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return LangObs;
    }


}
