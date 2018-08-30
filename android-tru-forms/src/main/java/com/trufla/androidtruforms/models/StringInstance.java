package com.trufla.androidtruforms.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.truviews.TruDatePickerView;
import com.trufla.androidtruforms.truviews.TruDateTimePickerView;
import com.trufla.androidtruforms.truviews.TruLocationView;
import com.trufla.androidtruforms.truviews.TruPhotoPickerView;
import com.trufla.androidtruforms.truviews.TruStringView;

/**
 * Created by ohefny on 6/26/18.
 */

public class StringInstance extends SchemaInstance {
    //Date,Image,textarea
    @SerializedName("format")
    protected String format;
    @SerializedName("maxLength")
    protected int maxLength;
    @SerializedName("minLength")
    protected int minLength;
    @SerializedName("pattern")
    protected String pattern;

    @Override
    public TruStringView getViewBuilder(Context context) {
        if (TruUtils.isEmpty(format))
            return new TruStringView(context, this);
        switch (format) {
            case SchemaKeywords.StringFormats.DATE:
                return new TruDatePickerView(context, this);
            case SchemaKeywords.StringFormats.DATE_TIME:
                return new TruDateTimePickerView(context, this);
            case SchemaKeywords.StringFormats.PHOTO:
                return new TruPhotoPickerView(context, this);
            case SchemaKeywords.StringFormats.MAP_LOCATION:
                return new TruLocationView(context, this);
            default:
                return new TruStringView(context,this);
        }

    }
}
