package com.trufla.androidtruforms.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.internal.$Gson$Preconditions;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import com.snatik.storage.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;

//import com.pdfview.PDFView;

public class PDFUtil {

    public static boolean compressPDF( String source, String output_path) {

            try {
                PdfReader reader = new PdfReader(source);
                PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(output_path), PdfWriter.VERSION_1_5);
                pdfStamper.setFullCompression();
                pdfStamper.close();
                return true;
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
                return false;
            }

    }


   public static Bitmap generateImageFromPdf(Uri pdfUri, Context context) {
        int pageNumber = 0;
        Bitmap bmp = null;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        try {
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
            Log.e("PDF THUMB" , e.getMessage());
        }

        return bmp ;
    }

    private static Storage storage;

    public static String savePDF(String docBase64,Context context)
    {
        storage = new Storage(context);
        byte[] docArray = Base64.decode(docBase64, Base64.DEFAULT);
        String path = storage.getInternalCacheDirectory().concat("/").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
        try {
            storage.createFile(path, docArray);
        }catch(Exception e){

            e.printStackTrace();
        }

       return "file://" + path ;
    }




}
