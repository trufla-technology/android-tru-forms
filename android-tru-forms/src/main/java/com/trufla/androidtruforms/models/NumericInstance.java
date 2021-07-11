package com.trufla.androidtruforms.models;

import android.content.Context;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruNumericView;

/**
 * Created by ohefny on 6/26/18.
 */

@Keep
public class NumericInstance extends SchemaInstance {
    protected double maximum;
    protected double minimum;
    protected double exclusiveMaximum;
    protected double exclusiveMinimum;
    protected String pattern;
    @SerializedName("placeholder")
    protected String placeholder;

    public NumericInstance(){

    }

    @Override
    public Object getDefaultConst() {
        return "N/A";
    }

    public NumericInstance(NumericInstance instance) {
        super(instance);
        this.maximum=instance.maximum;
        this.minimum=instance.minimum;
        this.exclusiveMaximum=instance.exclusiveMaximum;
        this.exclusiveMinimum=instance.exclusiveMinimum;
        this.pattern=instance.pattern;
    }

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

    @Override
    public TruNumericView getViewBuilder(Context context) {
        return new TruNumericView(context,this);
    }

    public String getPattern() {
        return pattern;
    }

    public String getPlaceholder() { return placeholder; }

}
