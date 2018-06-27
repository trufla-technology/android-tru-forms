package com.trufla.androidtruforms;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by ohefny on 6/27/18.
 */

public class TruUtils {

    /**
     * Checks to see if EditText contains whitespace or no text
     *
     * @param et the EditText
     * @return true if EditText contains whitespace or no text otherwise false
     */
    public static boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(et.getText().toString().trim());
    }

    /**
     * Checks to see if text is null or contains whitespace or no content
     *
     * @param charSequence
     * @return true if text contains whitespace or no content otherwise false
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.equals("");
    }

    /**
     * Get the EditText text trimmed
     *
     * @param et
     * @return the EditText text trimmed
     */
    public static String getText(EditText et) {
        return et.getText().toString().trim();
    }

    public static String getText(EditText et,String defaultText){
        return isEmpty(et.getText()) ? defaultText : et.getText().toString();
    }
    public static String getText(String val,String defaultText){
        if(val==null||val.equals(""))
            return defaultText;

        return val;
    }
}
