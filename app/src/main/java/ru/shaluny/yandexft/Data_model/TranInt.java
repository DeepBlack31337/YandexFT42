package ru.shaluny.yandexft.Data_model;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.shaluny.yandexft.Data_model.LangModel;
import ru.shaluny.yandexft.Data_model.PostModel;

/**
 * Created by administrator on 14.04.2017.
 */

public interface TranInt {
        @POST("api/v1.5/tr.json/translate")
        Observable<PostModel> getData(@Query("key") String resKey, @Query("text") String resText, @Query("lang") String resLang);
        @POST("api/v1.5/tr.json/getLangs")
        Observable<LangModel> getLangs(@Query("key") String resKey, @Query("ui") String resText);

}
