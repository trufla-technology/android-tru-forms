package com.trufla.androidtruforms;

import com.trufla.androidtruforms.utils.TruUtils;

/**
 * Created by Mohamed Salah on 10,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

public class SharedData
{
    private static SharedData instance;

    // Global variable
    private String data;
    private String defaultLanguage;

    // Restrict the constructor from being instantiated
    private SharedData() {
    }

    public static synchronized SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public void setData(String d) {
        this.data = d;
    }

    public String getData() {
        return this.data;
    }

    public void setLanguage(String language)
    {
        this.defaultLanguage = language;
    }

    public String getDefaultLanguage()
    {
        //Check if default language is null or empty I'll return "en" as a default language
        return TruUtils.setDefaultUserLanguage(this.defaultLanguage);
    }

}
