package ru.shaluny.yandexft.UI_model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import io.realm.RealmResults;
import io.realm.Sort;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.R;

import static ru.shaluny.yandexft.Data_model.RealmQuery.writeToRealm;
import static ru.shaluny.yandexft.Pres_model.GlobalVars.realm;

/**
 * Created by administrator on 12.05.2017.
 */

public class third_page_view extends LinearLayout {
    public static RecycleViewAdapter mAdapter;

    public third_page_view(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //////////берем избранное
        RealmResults<CacheTable> mResults = realm.where(CacheTable.class).equalTo("izbr",true).findAllSorted("date", Sort.DESCENDING);

//Hello

        RecyclerView mRecycler = (RecyclerView) findViewById(R.id.histRecycler);
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
                        mResults.get(position).setIzbr(true);
                        writeToRealm(mResults.get(position));
                    } else {
                        rbar.setRating(0);
                        /////апдейтим рилм запись, при этом берем ее из выборки и изменяем, дальше апдейтим
                        mResults.get(position).setIzbr(false);
                        writeToRealm(mResults.get(position));
                    }
                }
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
