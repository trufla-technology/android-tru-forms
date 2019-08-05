package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
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
    String base64Image = "";

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
//        String title = mView.getResources().getString(R.string.add_photo_label, instance.getPresentationTitle());
        String title = instance.getPresentationTitle()+"";
        ((TextView) mView.findViewById(R.id.title)).setText(title);
    }

    @NonNull
    private View.OnClickListener getOnViewClickedListener() {
        return (v) -> {
            FormContract hostActivity = getFormContract(v);
            if (hostActivity != null)
                hostActivity.openImagePicker(getImagePickedListener());

        };
    }

    @NonNull
    private TruConsumer<String> getImagePickedListener() {
        return (bitmapPath) -> {
            mBitmapPath = bitmapPath;
            setImageToView(BitmapUtils.loadBitmapFromPath(bitmapPath));
            base64Image = BitmapUtils.downScaleImageAndConvertToWebPAsBase64(mContext, Uri.parse(mBitmapPath), 150, 170);
        };
    }

    private void setImageToView(Bitmap bitmap) {
        mView.findViewById(R.id.photo_thumb_container).setVisibility(View.GONE);
        ((ImageView) mView.findViewById(R.id.photo)).setImageBitmap(bitmap);
        mView.findViewById(R.id.photo_container).setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_photo_pick_view;
    }

    @NonNull
    @Override
    protected String extractData()
    {
        return base64Image;
//        return BitmapUtils.downScaleImageAndConvertToWebPAsBase64(mContext, Uri.parse(mBitmapPath), 200, 200);
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

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String && !TextUtils.isEmpty(constItem.toString())) {
            try {
                Bitmap img = BitmapUtils.decodeBase64ToBitmap(constItem.toString());
                setImageToView(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mView.findViewById(R.id.photo_remove_icon).setVisibility(View.GONE);
        mView.setEnabled(false);
    }

}
