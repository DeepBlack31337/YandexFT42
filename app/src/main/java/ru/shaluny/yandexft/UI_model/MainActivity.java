package ru.shaluny.yandexft.UI_model;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import io.reactivex.observers.DisposableObserver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.Data_model.LangModel;
import ru.shaluny.yandexft.Data_model.LangTable;
import ru.shaluny.yandexft.R;



import static ru.shaluny.yandexft.Data_model.InetQuery.LangsQuery;
import static ru.shaluny.yandexft.Data_model.RealmQuery.writeToRealmLang;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.imLang;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.imLangM;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.key;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang1;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.lang2;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.realm;
import static ru.shaluny.yandexft.Pres_model.SecurityCheck.InetCheck;
import static ru.shaluny.yandexft.Pres_model.SecurityCheck.SecCheck;


public class MainActivity extends AppCompatActivity {

    // Контейнер для мест
    private List<String> titles = new ArrayList<String>();
    private String trWord;
    public static Context mcont;
    private ArrayList<String> imText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Прозрачный статус-бар
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        initRealm();

/////////////Заполним языки и занесем их в рилм

        /////Очищаем массивы куда положим допустимые языки
        imLangM.clear();
        imLang.clear();

        ///Берем контекст для тоаст
        Context cont = this;

        /////////Проверка на разрешения и интернет
        if (SecCheck(cont, this) && InetCheck(cont)) {
            //////////Строим запрос
            //Если onError, повторяем 3 раза
            LangsQuery(key)
                    .subscribe(new DisposableObserver<LangModel>() {
                        @Override
                        public void onNext(LangModel langModel) {
                            String lang = langModel.getLangs().toString();
                            //lang = lang.replaceAll("=",":");
                            //lang = "'"+lang+"'";
                            Log.d("LANGS", lang);

                            /////Переводим Обьект в gson
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            String strJson = gson.toJson(langModel.getLangs());

                            /////присваиваем обьекту JSON и парсим в базу
                            JSONObject jsonObject;

                            try {
                                jsonObject = new JSONObject(strJson);
                                //for (jsonObject.keys()  )
                                //jsonObject.keys().forEachRemaining();

                                Iterator<String> iterator = jsonObject.keys();

                                while (iterator.hasNext()) {
                                    String rkey = iterator.next();
                                    String rlang = jsonObject.getString(rkey);

                                    //////Пишем в LangTable, primary Lang
                                    LangTable lngtable = new LangTable();
                                    lngtable.setLang(rkey);
                                    lngtable.setLangm(rlang);

                                    writeToRealmLang(lngtable);
                                    //Log.d("LANGS", rkey);
                                    //Log.d("LANGS",jsonObject.getString(rkey));
                                }

                            } catch (JSONException e) {
                                Log.d("LANGS_ERROR", e.getMessage());
                            }

                            //LangTable lngtable = LangRead("*");

                            Log.d("LANG_BASE", String.valueOf(realm.where(LangTable.class).count()));

                            ///TODO: Не знал как преобразовать в обьект для распарса, придумал костыль, оставил на память Я.С.
                            //////////////Не знаю пока как взять массив обьектов из retrofit JSOP, пишет ошибку при присваивании JSONObject,JSONArray
//                                   for (int i=0; i<=lang.length()-1;i++) {
//                                       imLang.add(lang.substring(lang.indexOf('=') - 2, 3));
//                                       imLangM.add(lang.substring(lang.indexOf('=')+1, lang.indexOf(",")));
//                                       lang = lang.substring(lang.indexOf(",")+1);
//                                       if (lang.indexOf(',')<0) {break;}
//                                   }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("LANGS ERROR", e.getMessage());
                            Toast.makeText(cont, "Ошибка загрузки списка языков", Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else { Toast.makeText(cont,"Проверьте разрешения на интернет или доступность интернета!",Toast.LENGTH_LONG); }

        Spinner spinner = (Spinner) findViewById(R.id.firstT);
        Spinner spinner2 = (Spinner) findViewById(R.id.secondT);

        //RealmResults<CoordinateObject> сoo
        RealmResults<LangTable> lngtable = realm.where(LangTable.class).findAllSorted("langm",Sort.ASCENDING);

        //Log.d("ARRAY SIZE", String.valueOf(lngtable.size()));
        //imText = lngtable.toArray(new ArrayList[lngtable.size()]);
        //Log.d("ARRAY CAST", imText.toString());

        imText = new ArrayList<>(lngtable.size());

        for (int i=0;i<=lngtable.size()-1;i++) {
            imText.add(lngtable.get(i).getLangm());
        }

        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new
                ArrayAdapter(this, R.layout.spinner_row, R.id.spin_row, imText);
        adapter.setDropDownViewResource(R.layout.spinner_row);

        // Вызываем адаптер
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner.setSelection(3);
        spinner2.setSelection(63);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView spinrow = (TextView) view.findViewById(R.id.spin_row);
                Log.d("SPIN_SELECT", spinrow.getText().toString());
                lang1 = lngtable.get(position).getLang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView spinrow = (TextView) view.findViewById(R.id.spin_row);
                Log.d("SPIN_SELECT2", spinrow.getText().toString());
                lang2 = lngtable.get(position).getLang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


                mcont = this;
        // Настройка тулбара - определяется в разметке
        //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //      if(toolbar != null)
        //      toolbar.setTitle("Яндекс переводчик");

        // Установка тулбара в качестве экшн-бара,
        // при этом экшн-бар скрывается через стиль приложения
        //setSupportActionBar(toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Находим пейджер
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);

        titles.clear();
        titles.add("ГЛАВНАЯ");
        titles.add("ИСТОРИЯ");
        titles.add("ИЗБРАННОЕ");


        // Адаптер
        //Тут все веселее, буду накачивать свои странички
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(titles);

        // Устанавливаем адаптер пейджеру
        mPager.setAdapter(mPagerAdapter);

        // Устанавливаем Toolbar в качестве ActionBar
        // ActionBar удаляется с помощью темы в styles.xml
        setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(mPager);

    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("YandexFT.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
        CacheTable rctable = new CacheTable();


    }

    private void setupViewPager(ViewPager viewPager) {
        //ViewPagerAdapter adapter = new ViewPagerAdapter()
    }

    public static void SetText(View view, String text) {

        Log.d("GET_RESPONSE2", text);
        TextView textView = (TextView) view.findViewById(R.id.textView2);
        textView.setText(text);

    }

}
