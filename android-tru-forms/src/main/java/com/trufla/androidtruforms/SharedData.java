package com.trufla.androidtruforms;

import com.trufla.androidtruforms.utils.TruUtils;

import java.util.HashMap;

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
    private String base64_pdf ;
    private String base64_image ;


    public HashMap<String, String> getBase64() {
        return base64;
    }

    public void setBase64(HashMap<String, String> base64) {
        this.base64 = base64;
    }

    private  HashMap<String,String> base64 ;

    // Restrict the constructor from being instantiated
    private SharedData() {
        base64 = new HashMap<>();
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

    public String getBase64_pdf() {
        return base64_pdf;
    }

    public void setBase64_pdf(String base64_pdf) {
        this.base64_pdf = base64_pdf;
    }

    public String getBase64_image() {
        return base64_image;
    }

    public void setBase64_image(String base64_image) {
        this.base64_image = base64_image;
    }
}
