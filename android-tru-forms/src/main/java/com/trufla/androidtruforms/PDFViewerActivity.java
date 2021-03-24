package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

//import com.pdfview.PDFView;
import com.github.barteksc.pdfviewer.PDFView;
import com.snatik.storage.Storage;
import com.trufla.androidtruforms.truviews.TruPhotoPickerView;
import com.trufla.androidtruforms.utils.PDFUtil;

import java.io.File;

public class PDFViewerActivity extends Activity {

   // PDFView pdfView ;
    PDFView pdfView;
    Storage storage ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pdfviewer);
        pdfView =  findViewById(R.id.doc_pdf_viewer);
        String base64 = SharedData.getInstance().getBase64_pdf();
        storage = new Storage(this);
        loadPDF(base64);

    }

    public  void loadPDF(String docBase64)
    {
        byte[] docArray = Base64.decode(docBase64, Base64.DEFAULT);
        String path = storage.getInternalCacheDirectory().concat("/").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
        storage.createFile(path, docArray);

        if(storage.isDirectoryExists(path))
        {
            File file = storage.getFile(path);
            showProgressBar(false);

            if(file != null)
                pdfView.fromFile(file).load();

            // pdfView.fromFile(file).show();
        }else {
            showProgressBar(false);
            Toast.makeText(this, "File Corrupted", Toast.LENGTH_LONG).show();
        }

    }

    public void showProgressBar(boolean isShow)
    {
        if(isShow)
            findViewById(R.id.document_progress).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.document_progress).setVisibility(View.GONE);
    }

    public void onClosePDFViewer(View view){

        this.finish();

    }

}
