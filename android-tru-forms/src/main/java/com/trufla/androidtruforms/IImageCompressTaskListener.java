package com.trufla.androidtruforms;

import java.io.File;
import java.util.List;

/**
 * Created by Mohamed Salah on 18,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

public interface IImageCompressTaskListener {

    void onComplete(List<File> compressed, String uriPath);

    void onError(Throwable error);
}