package com.example.macker;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class CreateHelper {
    Context context;
    RelativeLayout saveScreen;
    CreateHelper(Context context) {
        this.context = context;
    }
    public void getScreen(RelativeLayout saveScreen){
        this.saveScreen = saveScreen;
    }

    public void copy(String copyTxt) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("key", copyTxt);
        clipboardManager.setPrimaryClip(clipData);
        //Toast.makeText(context, "Coped "+positionString, Toast.LENGTH_SHORT).show();
    }

    public void permission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    public void imageSave(boolean imageShare) {

        saveScreen.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(saveScreen.getDrawingCache());
        saveScreen.setDrawingCacheEnabled(false);
        //folder create
        String fillLocation = Environment.getExternalStorageDirectory() + "/Download/";
        File createFill = new File(fillLocation);
        if (!createFill.exists()) {
            createFill.mkdirs();
        }

        String filepath = fillLocation + "/" + "App Name " + Calendar.getInstance().getTime().toString() + ".jpg";
        File fileScreenShot = new File(filepath);

        FileOutputStream fileOutputStream = null;
        try {
            //Toast.makeText(context, "Saved - " + filepath, Toast.LENGTH_SHORT).show();
            fileOutputStream = new FileOutputStream(fileScreenShot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imageShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.fromFile(fileScreenShot);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/jpg");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, "send"));
        }

    }

}
