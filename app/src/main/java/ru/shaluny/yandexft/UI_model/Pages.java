package ru.shaluny.yandexft.UI_model;

import android.view.View;
import android.widget.EditText;

import ru.shaluny.yandexft.R;

/**
 * Created by administrator on 14.04.2017.
 */

public class Pages {
    private EditText editText;
    private View view;

    public Pages(View v) {
        this.view = v;
    }

    public View getEditText() {
        editText = (EditText) view.findViewById(R.id.editText);
        return editText;
    }

    public void setEditText() {
        EditText edit = (EditText) view.findViewById(R.id.editText);
        this.editText = edit;
    }
}
