package com.trufla.androidtruforms.schema_models;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.schema_views.TruNumericView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

public class NumericInstance extends SchemaInstance {
    protected double maximum;
    protected double minimum;
    protected double exclusiveMaximum;
    protected double exclusiveMinimum;
    @SerializedName("enum")
    protected ArrayList<Double> enumArray; //instance of String or Number or Boolean
    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getExclusiveMaximum() {
        return exclusiveMaximum;
    }

    public void setExclusiveMaximum(double exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    public double getExclusiveMinimum() {
        return exclusiveMinimum;
    }

    public void setExclusiveMinimum(double exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    public ArrayList<Double> getEnumArray() {
        return enumArray;
    }

    @Override
    public TruNumericView getViewBuilder() {
        return new TruNumericView(this);
    }
}
