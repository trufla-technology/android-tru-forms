package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;

public class SchemaViews {
    public static TruFormFragment newSchemaFragmentInstance(int schemaType, String schemaString, Context context) throws UnableToParseSchemaException {
        TruFormFragment formFragment = TruFormFragment.newInstance(schemaType, schemaString.toString());
        return formFragment;
    }

    public static TruFormFragment newSchemaFragmentWithConstValuesInstance(String schemaString, String jsonValue, Context context) throws UnableToParseSchemaException {
        TruFormFragment formFragment = TruFormFragment.newInstanceWithConstJson(schemaString.toString(), jsonValue);
        return formFragment;
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragment(int schemaType, String schemaString, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId) throws UnableToParseSchemaException {
        TruFormFragment formFragment = newSchemaFragmentInstance(schemaType, schemaString, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragmentWithConstValue(String schemaString, String jsonValues, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId) throws UnableToParseSchemaException {
        TruFormFragment formFragment = newSchemaFragmentWithConstValuesInstance(schemaString, jsonValues, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static void startActivityForResult(Activity context, String schemaString) {
        TruFormActivity.startActivityForFormResult(context, schemaString);
    }

    public static void startActivityForResult(Fragment fragment, String schemaString) {
        TruFormActivity.startActivityForFormResult(fragment, schemaString);
    }

    public void startActivityToRenderConstSchema(Activity activity, String schemaString, String constValue) {
        TruFormActivity truFormActivity = new TruFormActivity();
        truFormActivity.onPassData(schemaString, constValue);
        truFormActivity.startActivityToRenderConstSchema(activity, schemaString, constValue);
    }

    public void startActivityToRenderConstSchema(Fragment fragment, String schemaString, String constValue) {
        TruFormActivity truFormActivity = new TruFormActivity();
        truFormActivity.onPassData(schemaString, constValue);
        truFormActivity.startActivityToRenderConstSchema(fragment, schemaString, constValue);
    }

}