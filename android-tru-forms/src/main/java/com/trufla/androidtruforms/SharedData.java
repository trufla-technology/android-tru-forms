package com.trufla.androidtruforms;

/**
 * Created by Mohamed Salah on 10,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */
public class SharedData {

    private static SharedData instance;

    // Global variable
    private String data;

    // Restrict the constructor from being instantiated
    private SharedData() {
    }

    public void setData(String d) {
        this.data = d;
    }

    public String getData() {
        return this.data;
    }

    public static synchronized SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }
}
