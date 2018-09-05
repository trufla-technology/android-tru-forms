package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.BitmapUtils;
import com.trufla.androidtruforms.utils.ColorFactory;

public class TruPhotoPickerView extends TruStringView {
    String mBitmapPath;

    public TruPhotoPickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        ((CardView) mView.findViewById(R.id.photo_thumb_container)).setCardBackgroundColor(ColorFactory.getTransparentColor(R.color.colorPrimary, mContext, 25));
        mView.setOnClickListener(getOnViewClickedListener());
        mView.findViewById(R.id.photo_remove_icon).setOnClickListener(getOnRemoveImageListener());

    }

    @Override
    protected void setInstanceData() {
        String title=mView.getResources().getString(R.string.add_photo_label,instance.getPresentationTitle());
        ((TextView) mView.findViewById(R.id.title)).setText(title);
    }

    @NonNull
    private View.OnClickListener getOnViewClickedListener() {
        return (v) -> {
            FormContract hostActivity = getTruFormHostActivity(v);
            if (hostActivity != null)
                hostActivity.openImagePicker(getImagePickedListener());

        };
    }

    @NonNull
    private TruConsumer<String> getImagePickedListener() {
        return (bitmapPath) -> {
            mBitmapPath = bitmapPath;
            mView.findViewById(R.id.photo_thumb_container).setVisibility(View.GONE);
            ((ImageView) mView.findViewById(R.id.photo)).setImageBitmap(BitmapUtils.loadBitmapFromPath(bitmapPath));
            mView.findViewById(R.id.photo_container).setVisibility(View.VISIBLE);

        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_photo_pick_view;
    }

    @Override
    public View build() {
        return super.build();
    }

    @NonNull
    @Override
    protected String extractData() {
        return BitmapUtils.downScaleImageAndConvertToWebPAsBase64(mContext, Uri.parse(mBitmapPath), 200, 200);
    }

    public View.OnClickListener getOnRemoveImageListener() {
        return (v) -> {
            mBitmapPath = null;
            mView.findViewById(R.id.photo_thumb_container).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.photo_container).setVisibility(View.GONE);

        };
    }

    @Override
    public LinearLayout.LayoutParams getLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }
}
