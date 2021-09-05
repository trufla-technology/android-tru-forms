package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;
import com.trufla.androidtruforms.truviews.TruPhotoPickerView;
import com.trufla.androidtruforms.utils.BitmapUtils;

public class PhotoViewerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer);

        String key =  getIntent().getStringExtra("bitmap");
        String base64 = SharedData.getInstance().getBase64().get(key);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        Bitmap bitmap = BitmapUtils.decodeBase64ToBitmap(base64);
        photoView.setImageBitmap(bitmap);

    }

    public void onClosePhotoViewer(View view){

         this.finish();

    }
}
