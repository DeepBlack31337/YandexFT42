package ru.shaluny.yandexft.UI_model;

/**
 * Created by administrator on 24.04.2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.shaluny.yandexft.Data_model.CacheTable;
import ru.shaluny.yandexft.R;


/**
 * Created by administrator on 05.04.2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
//public class RecyclerViewAdapter extends RecyclerView.Adapter<Wifi_table,RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<CacheTable> mResults;


    public RecycleViewAdapter(Context context, Realm realm, RealmResults<CacheTable> results) {
        mRealm = realm;
        mInflater = LayoutInflater.from(context);
        setResults(results);

    }

    public void setResults(RealmResults<CacheTable> results) {
        mResults = results;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mInflater.inflate(R.layout.recyclerview_adapter, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CacheTable table = mResults.get(position);
        holder.lang.setText(table.getFirst());
        holder.langm.setText(table.getSecond());
        if (table.getIzbr()!=null && table.getIzbr() && holder.rating!=null) {
            holder.rating.setRating(1); }
        Log.i("HOLDER_SETTEXT", table.getFirst());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lang;
        public TextView langm;
        public RatingBar rating;

        public ViewHolder(View container) {
            super(container);
            this.lang = (TextView) container.findViewById(R.id.lang);
            this.langm = (TextView) container.findViewById(R.id.langm);
            this.rating = (RatingBar) container.findViewById(R.id.ratingBar);
        }

        public void setLang(String text) {
            lang.setText(text);
        }

        public void setLangm(String text) {
            langm.setText(text);
        }

        public void setRating(int i) { rating.setRating(i); }

    }


    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void setUpdate() {
        notifyDataSetChanged();
    }


}






