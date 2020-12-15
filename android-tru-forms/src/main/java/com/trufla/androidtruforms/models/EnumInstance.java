package com.trufla.androidtruforms.models;

import android.content.Context;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SharedData;
import com.trufla.androidtruforms.truviews.TruEnumDataView;
import com.trufla.androidtruforms.truviews.TruEnumView;

import java.util.ArrayList;

/**
 * Created by ohefny on 7/3/18.
 */

@Keep
public class EnumInstance<T> extends SchemaInstance
{
    @SerializedName("enum")
    protected ArrayList<T> enumVals; //instance of String or Number or Boolean

    @SerializedName("enumNames")
    protected ArrayList<ArrayList<TitleInstance>> enumNamesList; //instance of String or Number or Boolean

    @SerializedName("_data")
    protected DataInstance dataInstance;

    protected ArrayList<String> myEnumNa;

    private static SharedData sharedData;

//    @SerializedName("enumNames")
//    protected ArrayList<String> enumNames; //instance of String or Number or Boolean


    public EnumInstance(){
    }

    @Override
    public Object getDefaultConst() {
        return null;
    }

    public EnumInstance(EnumInstance<T> copyInstance)
    {
        super(copyInstance);
        this.enumVals= new ArrayList<>(copyInstance.enumVals);
        this.myEnumNa =new ArrayList<>(copyInstance.myEnumNa);
        this.dataInstance=new DataInstance(copyInstance.getDataInstance());
    }

    public boolean enumExists()
    {
        myEnumNa = getEnumList(enumNamesList);
        return enumVals != null && !enumVals.isEmpty();
    }

    @Override
    public TruEnumView getViewBuilder(Context context)
    {
        if (dataInstance == null)
            return new TruEnumView(context, this);
        else
            return new TruEnumDataView(context, this);
    }

    public ArrayList<String> getEnumDisplayedNames()
    {
        ArrayList<String> displayedNames = new ArrayList<>();
        myEnumNa = getEnumList(enumNamesList);
        if (myEnumNa != null && !myEnumNa.isEmpty())
            return myEnumNa;

        if(enumVals.size() > 0)
        {
            if (enumVals.get(0) instanceof String)
                return (ArrayList<String>) enumVals;

            if (enumVals.get(0) instanceof Number) {
                for (T d : enumVals)
                {
                    String value = String.valueOf(d).replace(".0","");
                    displayedNames.add(value);
                }
            }
        }
        return displayedNames;
    }

    public ArrayList<String> getEnumList(ArrayList<ArrayList<TitleInstance>> myEnumsList)
    {
        sharedData = SharedData.getInstance();
        String myLanguage = sharedData.getDefaultLanguage();

        ArrayList<String> newEnumList = new ArrayList<>();

        if(myEnumsList != null)
        {
            for(int listIndex = 0; listIndex< myEnumsList.size(); listIndex++)
            {
                for (TitleInstance titleInstance: myEnumsList.get(listIndex))
                {
                    if(titleInstance.getLanguage().equals(myLanguage))
                        newEnumList.add(titleInstance.getTitleValue());
                }
            }
        }

        return newEnumList;
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

    public void setMyEnumNa(ArrayList<String> myEnumNa) {
        this.myEnumNa = myEnumNa;
    }

    public ArrayList<String> getMyEnumNa() {
        return myEnumNa;
    }
}
