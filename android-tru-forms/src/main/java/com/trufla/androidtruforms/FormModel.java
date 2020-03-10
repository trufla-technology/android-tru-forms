package com.trufla.androidtruforms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Salah on 10,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */
public class FormModel implements Parcelable {

    private String jsonStr;

    public FormModel(String jsonStr) {
        super();
        this.jsonStr = jsonStr;
    }

    public FormModel() {
        super();
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    protected FormModel(Parcel in) {
        jsonStr = in.readString();
    }

    public static final Creator<FormModel> CREATOR = new Creator<FormModel>() {
        @Override
        public FormModel createFromParcel(Parcel in) {
            return new FormModel(in);
        }

        @Override
        public FormModel[] newArray(int size) {
            return new FormModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(jsonStr);
    }
}
