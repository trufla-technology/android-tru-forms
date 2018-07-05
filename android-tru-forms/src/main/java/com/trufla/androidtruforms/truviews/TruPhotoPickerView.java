package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.StringInstance;

public class TruPhotoPickerView extends TruStringView{
    public TruPhotoPickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextView)mView.findViewById(R.id.title)).setText(instance.getPresentationTitle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_photo_pick_view;
    }

    @Override
    public View build() {
        return super.build();
    }
}
