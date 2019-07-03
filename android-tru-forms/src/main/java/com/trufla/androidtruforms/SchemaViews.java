package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;


public class SchemaViews
{
    private static TruFormFragment newSchemaFragmentInstance(int userId, int schemaType, String schemaString, Context context)
            throws UnableToParseSchemaException {
        return TruFormFragment.newInstance(userId, schemaType, schemaString);
    }

    private static TruFormFragment newSchemaFragmentWithConstValuesInstance(String schemaString, String jsonValue, Context context)
            throws UnableToParseSchemaException {
        return TruFormFragment.newInstanceWithConstJson(schemaString, jsonValue);
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragment
            (int userId, int schemaType, String schemaString, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId)
            throws UnableToParseSchemaException
    {
        TruFormFragment formFragment = newSchemaFragmentInstance(userId, schemaType, schemaString, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragmentWithConstValue
            (String schemaString, String jsonValues, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId)
            throws UnableToParseSchemaException {
        TruFormFragment formFragment = newSchemaFragmentWithConstValuesInstance(schemaString, jsonValues, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static void startActivityForResult(Activity context, String schemaString) {
        TruFormActivity.startActivityForFormResult(context, schemaString);
    }

    public static void startActivityForResult(Fragment fragment, String schemaString) {
        TruFormActivity.startActivityForFormResult(fragment, schemaString);
    }

    public static void startActivityToRenderConstSchema(Activity activity, String schemaString, String constValue) {
        TruFormActivity.startActivityToRenderConstSchema(activity, schemaString, constValue);
    }

    public static void startActivityToRenderConstSchema(Fragment fragment, String schemaString, String constValue) {
        TruFormActivity.startActivityToRenderConstSchema(fragment, schemaString, constValue);
    }
}