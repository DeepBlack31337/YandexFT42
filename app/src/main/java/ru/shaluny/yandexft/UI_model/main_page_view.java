package ru.shaluny.yandexft.UI_model;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.Data_model.DictInt;
import ru.shaluny.yandexft.Data_model.DictModel.DictPostModel;
import ru.shaluny.yandexft.Data_model.PostModel;
import ru.shaluny.yandexft.Pres_model.SecurityCheck;
import ru.shaluny.yandexft.R;
import ru.shaluny.yandexft.Data_model.TranInt;

import static ru.shaluny.yandexft.Data_model.CreateRetrofitObjects.crtRetrofitDict;
import static ru.shaluny.yandexft.Data_model.CreateRetrofitObjects.crtRetrofitTrans;
import static ru.shaluny.yandexft.Data_model.InetQuery.DictionaryQuery;
import static ru.shaluny.yandexft.Data_model.RealmQuery.CacheRead;
import static ru.shaluny.yandexft.Data_model.RealmQuery.InsertDictCache;
import static ru.shaluny.yandexft.Data_model.RealmQuery.InsertTransCache;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.keydict;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang1;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang2;
import static ru.shaluny.yandexft.Data_model.InetQuery.TranslateQuery;
import static ru.shaluny.yandexft.Pres_model.SecurityCheck.InetCheck;
import static ru.shaluny.yandexft.Pres_model.SecurityCheck.SecCheck;


/**
 * Created by administrator on 17.04.2017.
 */

public class main_page_view extends RelativeLayout {

    public static String etxt = "";
    private String str="";
    private String key = "trnsl.1.1.20170414T165202Z.442e7085636de5d9.556566446301a65e849da51fdb55a14e7ed681e9";
    private View view;
    private static MainActivity mainActivity;
    //private Observable<PostModel> call;

/*    private Observable<String> keyObserver;
    private Observable<Object> keyDebObserver;
    private Observable<List<Object>> bufkeyObserver;
    private PublishSubject<String> subject;*/

    public main_page_view(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = this;
        TextView txt1 = (TextView) view.findViewById(R.id.textView);
        TextView txt2 = (TextView) view.findViewById(R.id.textView2);
        EditText edtxt1 = (EditText) view.findViewById(R.id.editText);
        ProgressBar prgrs1 = (ProgressBar) view.findViewById(R.id.progress1);
        ProgressBar prgrs2 = (ProgressBar) view.findViewById(R.id.progress2);




        Log.d("NAKACHKA LAYOUT", "ON FINISH INFLATE");

        //txt1.setText("WAITING");
/*        EditText.OnKeyListener listKey = new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText editText = (EditText) v.findViewById(R.id.editText);
                etxt = editText.getText().toString();
                SendText(v, etxt);
                return false;
            }
        };*/
        //edtxt1.setOnKeyListener(this);
        //edtxt1.addTextChangedListener(this);

//Для уменьшения "простыни" и разделения mvp данный обсервабль надо разбить на классы, тут происходит взаимодействие с базой, с интернетом и с UI.
//Слушающий нажатия обсервабль оставим здесь как и изменения UI компонентов, уберем подготовку обьектов (рилм, запросы) в Data_model, уберем
//обсерверы в отдельные классы (Data_model), подписку на них оставим в UI_model с обработкой ответов, при этом работа с UI остается здесь а вот работа
//с рилм в Data_model.

        RxTextView.textChanges(edtxt1).debounce(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    Log.d("TEXT CHANGED", edtxt1.getText().toString());
                    txt1.setVisibility(TextView.INVISIBLE);

                    if (edtxt1!=null && !edtxt1.getText().toString().equals("")) {
                        Log.d("IF", "1");

                        ////////Создаю таблицы кеша, один для чтения второй для записи
                        final CacheTable wctable = new CacheTable();
                        CacheTable rctable = new CacheTable();

                        if (prgrs2 != null && rctable != null) {
                            Log.d("IF", "2");
                            String text = edtxt1.getText().toString();

                            Log.d("REALM CACHE", "Читаю кеш");
                            prgrs2.setVisibility(ProgressBar.VISIBLE);

                            /////Читаю кеш отдельной функцией
                            rctable = CacheRead(text, lang1 + "-" + lang2);

                            if (rctable != null && !rctable.getFirst().equals("")) {
                                Log.d("REALM CACHE", "Нашли: " + rctable.getUID());
                                Log.d("REALM CACHE", "+ " + rctable.getSecond());
                                prgrs2.setVisibility(ProgressBar.INVISIBLE);
                                //Вписываем перевод из кеша
                                txt2.setText(rctable.getSecond());
                                if (txt1 != null && rctable.getAnn() != null && !rctable.getAnn().equals("")) {
                                    txt1.setVisibility(VISIBLE);
                                    txt1.setText(Html.fromHtml(rctable.getAnn()));
                                }
                            } else {
                                //Если кеша нет то делаем запрос в инет
                                ////Этот запрос в интернет только после всех проверок

                                ////Проверка на разрешения и интернет
                                if (SecCheck(getContext(), mainActivity) && InetCheck(getContext())) {

                                    //Вот тут происходит сам запрос, его прячем в отдельный класс и разделяем
                                    //работу с кешем и обновление UI

                                    TranslateQuery(key, text, lang1 + "-" + lang2)
                                            .subscribe(new DisposableObserver<PostModel>() {

                                                @Override
                                                public void onNext(PostModel value) {
                                                    //Тут мы получаем ответ, кешируем в рилме, выводим в UI
                                                    Log.d("GET_RESPONSE", value.getText().toString());
                                                    String code = value.getCode();
                                                    String resptext = value.getText().toString();
                                                    if (code.equals("200") && txt2 != null && !resptext.equals("")) {

                                                        /////Вставляю кеш переводчика
                                                        InsertTransCache(wctable, text, resptext, lang1 + "-" + lang2);

                                                        //////Вставляем в UI, обрезав скобки
                                                        txt2.setText(resptext.substring(1, resptext.length() - 1));
                                                        prgrs1.setVisibility(ProgressBar.VISIBLE);


                                                        //////////////////////////////////////////////////////////
                                                        //////////////////////////////////////////////////////////
                                                        //////Запрос в словарь
                                                        DictionaryQuery(lang1 + "-" + lang2, text)
                                                                .subscribe(new DisposableObserver<DictPostModel>() {
                                                                    @Override
                                                                    public void onNext(DictPostModel dictPostModel) {

                                                                        ////////Разбираем пришедший объект словаря

                                                                        if (dictPostModel.getDef() != null && dictPostModel.getDef().size() != 0) {

                                                                            Log.d("DICTOBJECT", dictPostModel.getDef().get(0).getText());

                                                                            String txt = dictPostModel.getDef().get(0).getText();
                                                                            //SpannableString stxt = new SpannableString(txt);
                                                                            //stxt.setSpan(new ForegroundColorSpan(Color.GREEN), 48, 55, 0);

                                                                            String ts = dictPostModel.getDef().get(0).getTs();
                                                                            //List<Tr> tr = new ArrayList<>();

                                                                            //Spanned all;
                                                                            //all = ;

                                                                            List<String> trtext = new ArrayList<String>();
                                                                            trtext.clear();
                                                                            for (int i = 0; i <= dictPostModel.getDef().get(0).getTr().size() - 1; i++) {
                                                                                trtext.add(" " + dictPostModel.getDef().get(0).getTr().get(i).getText());
                                                                            }
                                                                            //trtext = dictPostModel.getDef().get(0).getTr().get(0).getMean();

                                                                            //////Пишем в кеш, UID одинаковый будет так что заменит запись
                                                                            //rctable = new CacheTable();
                                                                            InsertDictCache(wctable, txt, ts, trtext, text, resptext, lang1 + "-" + lang2);

                                                                            txt1.setText(Html.fromHtml("<font color=RED><b>" + txt + "   <i>" + ts + "</i></font></b><br>" + trtext.toString().substring(1, trtext.toString().length() - 1)));

                                                                            //Обновляю историю при поиске
                                                                            if (third_page_view.mAdapter != null) {
                                                                                second_page_view.mAdapter.notifyDataSetChanged();
                                                                            } else {
                                                                                Log.d("ADAPTER", "НЕ НАЙДЕН");
                                                                            }


                                                                        }

                                                                        prgrs1.setVisibility(ProgressBar.INVISIBLE);
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        Log.d("DICT ERROR", e.getMessage());
                                                                        txt1.setVisibility(TextView.INVISIBLE);
                                                                        prgrs1.setVisibility(ProgressBar.INVISIBLE);
                                                                    }

                                                                    @Override
                                                                    public void onComplete() {
                                                                        txt1.setVisibility(TextView.VISIBLE);
                                                                    }
                                                                });
                                                    }

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    if (e != null) {
                                                        Log.d("GET_ERROR", e.getMessage());
                                                        prgrs2.setVisibility(ProgressBar.INVISIBLE);
                                                        txt2.setText("Ошибка подключения к интернету");
                                                    }
                                                }

                                                @Override
                                                public void onComplete() {
                                                    prgrs2.setVisibility(ProgressBar.INVISIBLE);
                                                }

                                            });
                                } else { Toast.makeText(getContext(),"Проверьте разрешения на интернет или доступность интернета!",Toast.LENGTH_LONG); }
                            }
                        }
                    }
                });

    }

}

