package com.trufla.androidtruforms.SchemaModels;

import com.trufla.androidtruforms.SchemaViews.SchemaBaseView;
import com.trufla.androidtruforms.SchemaViews.TruSectionView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

public class ObjectInstance extends SchemaInstance {
    int maxProperties;
    int minProperties;
    ArrayList<String>required;
    ArrayList<SchemaInstance>properties;

    @Override
    public TruSectionView getViewBuilder() {
        //todo see if we should make section return section view
        return new TruSectionView(this);
    }
   /* patternProperties , additionalProperties ,dependencies , propertyNames */
}
