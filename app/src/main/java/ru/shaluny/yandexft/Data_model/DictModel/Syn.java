
package ru.shaluny.yandexft.Data_model.DictModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Syn {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
