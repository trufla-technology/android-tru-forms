package com.trufla.androidtruforms;

import android.net.Uri;

import java.io.File;
import java.util.List;

public interface FileCompressTaskListener {

    void onComplete(String base64, Uri uriPath);

    void onError(Throwable error);

}
