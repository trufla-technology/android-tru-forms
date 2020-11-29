package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.utils.TruUtils;

/**
 * Created by Marina Wageed on 01,November,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

public class TitleInstance
{
    @SerializedName("language")
    protected String language;

    @SerializedName("value")
    protected String titleValue;

    public String getLanguage() {
        return TruUtils.checkIfStringIsEmpty(language);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitleValue() {
        return TruUtils.checkIfStringIsEmpty(titleValue);
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }
}
