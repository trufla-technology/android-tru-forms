package com.trufla.androidtruforms.models;

import android.content.Context;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruTimePickerView;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.truviews.TruDatePickerView;
import com.trufla.androidtruforms.truviews.TruDateTimePickerView;
import com.trufla.androidtruforms.truviews.TruLocationView;
import com.trufla.androidtruforms.truviews.TruPhotoPickerView;
import com.trufla.androidtruforms.truviews.TruStringView;

/**
 * Created by ohefny on 6/26/18.
 */

@Keep
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

    public StringInstance(){

    }

    @Override
    public Object getDefaultConst() {
        if(format!=null&&format.equals(SchemaKeywords.StringFormats.PHOTO))
            return DEFAULT_NA_IMAGE;
        return "N/A";
    }

    public StringInstance(StringInstance copyInstance) {
        super(copyInstance);
        this.format=copyInstance.getFormat();
        this.pattern=copyInstance.getPattern();
        this.maxLength=copyInstance.getMaxLength();
        this.minLength=copyInstance.getMinLength();
    }

    @Override
    public TruStringView getViewBuilder(Context context)
    {
        if (TruUtils.isEmpty(format))
            return new TruStringView(context, this, "");
        switch (format) {
            case SchemaKeywords.StringFormats.DATE:
                return new TruDatePickerView(context, this);
            case SchemaKeywords.StringFormats.TIME:
                return new TruTimePickerView(context, this);
            case SchemaKeywords.StringFormats.DATE_TIME:
                return new TruDateTimePickerView(context, this);
            case SchemaKeywords.StringFormats.PHOTO:
                return new TruPhotoPickerView(context, this);
            case SchemaKeywords.StringFormats.MAP_LOCATION:
                return new TruLocationView(context, this);
            case SchemaKeywords.StringFormats.EMAIL:
                return new TruStringView(context, this, "email");
            default:
                return new TruStringView(context,this, "");
        }
    }

    public String getFormat() {
        return format;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public String getPattern() {
        return pattern;
    }

    final private  String DEFAULT_NA_IMAGE="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";
}
