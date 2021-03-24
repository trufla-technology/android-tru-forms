package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.trufla.androidtruforms.FileCompressTask;
import com.trufla.androidtruforms.PDFViewerActivity;
import com.trufla.androidtruforms.PhotoViewerActivity;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.SharedData;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.ImageModel;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.BitmapUtils;
import com.trufla.androidtruforms.utils.ColorFactory;
import com.trufla.androidtruforms.utils.PDFUtil;


public class TruPhotoPickerView extends TruStringView {
    String base64Image = "";

    public TruPhotoPickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        ((CardView) mView.findViewById(R.id.photo_thumb_container)).setCardBackgroundColor(ColorFactory.getTransparentColor(R.color.colorPrimary, mContext, 25));
        mView.findViewById(R.id.photo_thumb_container).setOnClickListener(getOnViewClickedListener());
        mView.findViewById(R.id.photo_remove_icon).setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        mView.findViewById(R.id.photo_remove_icon).setOnClickListener(getOnRemoveImageListener());
    }

    @Override
    protected void setInstanceData() {
     //  String title = mView.getResources().getString(R.string.add_photo_label, instance.getPresentationTitle());
        String title = " *.PDF , JPG , PNG"; //instance.getPresentationTitle() +
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
    private TruConsumer<ImageModel> getImagePickedListener() {
        return (imageModel) ->
        {

            if(imageModel.getBase64()== null || !imageModel.getBase64().contains("pdf")) {
                // photo thumb
                if (imageModel.getImagePath().isEmpty())
                    setImageToView(imageModel.getImageBitmap());
                else
                    setImageToView(BitmapUtils.handleImageRotation(imageModel.getImagePath(), imageModel.getImageBitmap()));

                AsyncTask.execute(() -> base64Image = BitmapUtils.editAndConvertBitMapToBase64(imageModel.getImageBitmap(), imageModel.getImagePath()));

            }else {
                //pdf thumb
                Bitmap bmp = imageModel.getImageBitmap() ;
                if(bmp != null) {
                    setImageToView(imageModel.getImageBitmap());
                }else {
                    Bitmap icon = BitmapUtils.getBitmapFromDrawable(mContext, R.drawable.ic_pdf_file_icon);
                    setImageToView(icon);
                }

                    base64Image = imageModel.getBase64() ;

            }
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
    protected String extractData() {
        return base64Image;
    }

    public View.OnClickListener getOnRemoveImageListener() {
        return (v) -> {
            mView.findViewById(R.id.photo_thumb_container).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.photo_container).setVisibility(View.GONE);
        };
    }

    @Override
    public LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String && !TextUtils.isEmpty(constItem.toString())) {
            try {
                if(constItem.toString().startsWith(FileCompressTask.PDF_CONST)){
                   String[] str = constItem.toString().split(",");
                    Uri uri = Uri.parse(PDFUtil.savePDF(str[1],mContext))  ;
                    Bitmap bmp = PDFUtil.generateImageFromPdf(uri,mContext);
                  // Bitmap bmp = BitmapUtils.getBitmapFromDrawable(mContext,R.drawable.ic_pdf_file_icon);
                    setImageToView(bmp);
                    mView.setEnabled(true);
                    sharedData = SharedData.getInstance();
                    sharedData.setBase64_pdf(str[1]);
                    mView.findViewById(R.id.photo_container).setOnClickListener(view -> startPDFViewer(mContext));

                }else {
                    Bitmap img = BitmapUtils.decodeBase64ToBitmap(constItem.toString());
                    setImageToView(img);
                    mView.setEnabled(true);
                    sharedData = SharedData.getInstance();
                    sharedData.setBase64_image(constItem.toString());
                    mView.findViewById(R.id.photo_container).setOnClickListener(view -> startPhotoViewer(mContext));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mView.findViewById(R.id.photo_remove_icon).setVisibility(View.GONE);
    }

    SharedData sharedData ;

    private void startPhotoViewer( Context context) {

        Intent intent = new Intent(context, PhotoViewerActivity.class);
        context.startActivity(intent);
    }

    private void startPDFViewer( Context context) {

        Intent intent = new Intent(context, PDFViewerActivity.class);
        context.startActivity(intent);

    }


}
