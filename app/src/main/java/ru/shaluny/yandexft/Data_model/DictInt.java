package ru.shaluny.yandexft.Data_model;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.shaluny.yandexft.Data_model.DictModel.DictPostModel;

/**
 * Created by administrator on 23.04.2017.
 */

public interface DictInt {
    @POST("api/v1/dicservice.json/lookup")
    Observable<DictPostModel> getAnn(@Query("key") String resKey, @Query("lang") String resLang, @Query("text") String resText);
}
