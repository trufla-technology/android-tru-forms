package com.trufla.androidtruforms.SchemaModels;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SchemaViews.SchemaBaseView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaInstance {
    //the key of the object ex: "date_of_loss":{} here we use data_of_loss as title
    @SerializedName("title")
    protected String title;
    @SerializedName("type")
    protected String type="";
    @SerializedName("enum")
    protected ArrayList<Object> enumArray; //instance of String or Number or Boolean
    @SerializedName("const")
    protected Object constItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Object> getEnumArray() {
        return enumArray;
    }

    public void setEnumArray(ArrayList<Object> enumArray) {
        this.enumArray = enumArray;
    }

    public Object getConstItem() {
        return constItem;
    }

    public void setConstItem(Object constItem) {
        this.constItem = constItem;
    }
    public abstract <T extends SchemaBaseView> T getViewBuilder();
}
