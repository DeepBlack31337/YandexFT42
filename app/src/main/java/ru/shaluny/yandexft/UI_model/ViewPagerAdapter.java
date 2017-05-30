package ru.shaluny.yandexft.UI_model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.shaluny.yandexft.R;

/**
 * Created by administrator on 14.04.2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    //private List<Pages> pages;
    private List<String> titles = new ArrayList<>(3);
    public static String etext = "";
    private View lay=null;

    public ViewPagerAdapter(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();

        final LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup cont = container;
        int pos = position;

        //На всякий случай инициализация

        View layout = inflater.inflate(R.layout.main_page, cont, false);

        //Накачиваю страницы вьюпейджера в зависимости от их позиции

        switch (pos) {
            case 0: layout = inflater.inflate(R.layout.main_page, cont, false); break;
            case 1: layout = inflater.inflate(R.layout.second_page, cont, false); break;
            case 2: layout = inflater.inflate(R.layout.third_page, cont, false); break;
        }

        Log.d("NAKACHKA LAYOUT", String.valueOf(pos)+" STRANICA");

        // Возвращаем "надутый" и настроенный view
        container.addView(layout);

        return layout;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    // Вызывается пейджером если есть необходимость уничтожить view
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    // Чтобы определить, нет ли уже такого view
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
