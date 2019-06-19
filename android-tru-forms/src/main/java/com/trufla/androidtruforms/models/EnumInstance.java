package com.trufla.androidtruforms.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruEnumDataView;
import com.trufla.androidtruforms.truviews.TruEnumView;

import java.util.ArrayList;

/**
 * Created by ohefny on 7/3/18.
 */

public class EnumInstance<T> extends SchemaInstance {
    @SerializedName("enum")
    protected ArrayList<T> enumVals; //instance of String or Number or Boolean
    @SerializedName("enumNames")
    protected ArrayList<String> enumNames; //instance of String or Number or Boolean
    @SerializedName("_data")
    protected DataInstance dataInstance;

    public EnumInstance(){

    }

    @Override
    public Object getDefaultConst() {
        return null;
    }

    public EnumInstance(EnumInstance<T> copyInstance) {
        super(copyInstance);
        this.enumVals= new ArrayList<>(copyInstance.enumVals);
        this.enumNames=new ArrayList<>(copyInstance.enumNames);
        this.dataInstance=new DataInstance(copyInstance.getDataInstance());
    }


    public boolean enumExists() {
        return enumVals != null && !enumVals.isEmpty();
    }

    @Override
    public TruEnumView getViewBuilder(Context context) {
        if (dataInstance == null)   
            return new TruEnumView(context, this);
        else
            return new TruEnumDataView(context, this);
    }

    public ArrayList<String> getEnumDisplayedNames() {
        ArrayList<String> displayedNames = new ArrayList<>();
        if (enumNames != null && !enumNames.isEmpty())
            return enumNames;

        if(enumVals.size() > 0)
        {
            if (enumVals.get(0) instanceof String)
                return (ArrayList<String>) enumVals;

            if (enumVals.get(0) instanceof Number) {
                for (T d : enumVals)
                {
                    String value = String.valueOf(d).replace(".0","");
                    displayedNames.add(String.valueOf(value));
                }
            }
        }
        return displayedNames;
    }

    public DataInstance getDataInstance() {
        return dataInstance;
    }

    public void setEnumVals(ArrayList<T> enumVals) {
        this.enumVals = enumVals;
    }

    public ArrayList<T> getEnumVals() {
        return enumVals;
    }

    public void setEnumNames(ArrayList<String> enumNames) {
        this.enumNames = enumNames;
    }

    public ArrayList<String> getEnumNames() {
        return enumNames;
    }
}
