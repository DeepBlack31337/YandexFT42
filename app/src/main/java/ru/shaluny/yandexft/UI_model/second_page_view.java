package ru.shaluny.yandexft.UI_model;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.R;

import static ru.shaluny.yandexft.Pres_model.GlobalVars.realm;

/**
 * Created by administrator on 24.04.2017.
 */

public class second_page_view extends LinearLayout {
    public static RecycleViewAdapter mAdapter;


    public second_page_view(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ///////////Ищем кеш для истории, и тут нужно взять самую полную фразу, для этого нужно найти самую полную фразу из всех
        ///////////для этого нам нужен будет equal и сравнение по размеру при этом фраза без пробелов не сравнивается (слово)

        //////////берем все

        RealmResults<CacheTable> mResults = realm.where(CacheTable.class).findAllSorted("date", Sort.DESCENDING); //SortedAsync("Wifi_ssid");
        //////////обрабатываем через цикл TODO: придумать как это сделать запросом
        String oldUID="";
        String oldFirst="";
/*        for(int i=0;i<=mResults.size()-1;i++) {

            /////////Берем строчку, сравниваем UID
            if (mResults.get(i).getUID()!=oldUID) {
                /////Проверяем на пробелы
                if (mResults.get(i).getFirst().contains(" ")==true) {
                    ////Проверка на совпадения, вот тут еще вопрос..
                    if (mResults.get(i).getFirst().contentEquals(oldFirst)==true) {
                        ////Если совпали сравниваем размер
                        if (mResults.get(i).getFirst().length()<oldFirst.length()) {
                            //////Если размер у текущего меньше, удаляем из выборки
                            mResults.get(i).deleteFromRealm(); }
                        else {
                            //////Если размер у нового равен или больше, перезаписываем старый
                            oldFirst = mResults.get(i).getFirst();
                            }
                        }
                    }
                }
            oldUID = mResults.get(i).getUID();
        }*/




        RecyclerView mRecycler = (RecyclerView) findViewById(R.id.langRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecycleViewAdapter(getContext(), realm, mResults);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecycler.setItemAnimator(itemAnimator);

        mRecycler.addOnItemTouchListener(new RecycleViewClickListener(getContext(), new RecycleViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(MainActivity.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();
                //Тут все просто, не чекнут - удаляем если есть, чекнут - добавляем если нет

                TextView ssidview = (TextView) view.findViewById(R.id.lang);

                    //Toast.makeText(getContext(),"HELLO",Toast.LENGTH_SHORT).show();
                //Log.d("VIEW_CLICKED", String.valueOf(view.getId())+" "+String.valueOf(R.id.CardButton));

                RatingBar rbar = (RatingBar) view.findViewById(R.id.ratingBar);
                if (rbar!=null) {
                    if (rbar.getRating() == 0) {
                        rbar.setRating(1);
                        /////апдейтим рилм запись, при этом берем ее из выборки и изменяем, дальше апдейтим
                        realm.executeTransaction(transactionRealm -> {
                            mResults.get(position).setIzbr(true);
                            transactionRealm.copyToRealmOrUpdate(mResults);
                        });

                    } else {
                        rbar.setRating(0);
                        /////апдейтим рилм запись, при этом берем ее из выборки и изменяем, дальше апдейтим
                        realm.executeTransaction(transactionRealm -> {
                            mResults.get(position).setIzbr(false);
                            transactionRealm.copyToRealmOrUpdate(mResults);
                        });

                    }
                }

                mResults.addChangeListener(new RealmChangeListener<RealmResults<CacheTable>>() {
                    @Override
                    public void onChange(RealmResults<CacheTable> element) {
                        //Обновляю историю при поиске
                        if (third_page_view.mAdapter!=null) {
                            third_page_view.mAdapter.notifyDataSetChanged();
                        } else { Log.d("ADAPTER", "НЕ НАЙДЕН"); }
                    }
                });

                //RatingBar rbar = (RatingBar) findViewById(R.id.ratingBar);
//                rbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                    @Override
//                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                        Log.d("RAITING_CHANGED",String.valueOf(rating));
//                    }
//                });

            }
        }));

        //Set the Adapter to use timestamp as the item id for each row from our database
        mAdapter.setHasStableIds(true);
        mRecycler.setAdapter(mAdapter);



    }

}
