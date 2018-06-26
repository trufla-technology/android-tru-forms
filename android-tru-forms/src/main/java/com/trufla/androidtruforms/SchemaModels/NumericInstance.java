package com.trufla.androidtruforms.SchemaModels;

import com.trufla.androidtruforms.SchemaViews.SchemaBaseView;
import com.trufla.androidtruforms.SchemaViews.TruNumericView;

/**
 * Created by ohefny on 6/26/18.
 */

public class NumericInstance extends SchemaInstance {
    protected double maximum;
    protected double minimum;
    protected double exclusiveMaximum;
    protected double exclusiveMinimum;

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
    public TruNumericView getViewBuilder() {
        return new TruNumericView(this);
    }
}
