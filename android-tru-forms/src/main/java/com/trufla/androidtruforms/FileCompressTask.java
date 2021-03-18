package com.trufla.androidtruforms;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;

import com.lowagie.text.DocumentException;
import com.trufla.androidtruforms.utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.trufla.androidtruforms.utils.PDFUtil;

public class FileCompressTask implements Runnable{

    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private File result ;
    private FileCompressTaskListener mFileCompressTaskListener;

    private Uri uriPath;
    private  Uri compPath ;
    public static final String PDF_CONST = "data:application/pdf;base64," ;
                                   // "^data:([a-zA-Z0-9]+/[a-zA-Z0-9-.+]+).base64,.*
                                          //  "data:image/jpeg;base64,"

    public FileCompressTask(Context context, Uri path, FileCompressTaskListener fileCompressTaskListener) {

        mContext = context;

        mFileCompressTaskListener = fileCompressTaskListener;

        uriPath = path;

        compPath = path ;
    }

    String bae64 = "" ;
    Bitmap bmp ;
    @Override
    public void run() {


      /* get file size and compress
      Cursor returnCursor = mContext.getContentResolver().query(uriPath, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        if(sizeIndex > 300){
            PdfReader reader = new PdfReader(uriPath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(compPath+"_compressed"),PdfWriter.VERSION_1_5);
            stamper.setFullCompression();
            stamper.close();
            }*/

        try {
            InputStream in = mContext.getContentResolver().openInputStream(uriPath);
             byte[] bytes = getBytes(in);
             bae64 = PDF_CONST + Base64.encodeToString(bytes,Base64.DEFAULT).replaceAll("\n", "") ;
             bmp = PDFUtil.generateImageFromPdf(uriPath,mContext);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        //use Handler to post the result back to the main Thread
        mHandler.post(() -> {
            if (mFileCompressTaskListener != null)
                mFileCompressTaskListener.onComplete(bae64, uriPath,bmp);
        });

    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
