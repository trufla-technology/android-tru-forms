package com.trufla.androidtruforms;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.util.List;

public interface FileCompressTaskListener {

    void onComplete(String base64, Uri uriPath , Bitmap bmp);

    void onError(Throwable error);

}
