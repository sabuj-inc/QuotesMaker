package com.example.macker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class EditAccess {
    Context context;

    public EditAccess(Context context) {
        this.context = context;
    }

    //image save or share
    public void imageSave(RelativeLayout screen,boolean imageShare) {
        screen.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screen.getDrawingCache());
        screen.setDrawingCacheEnabled(false);
        //folder create
        final String fillLocation = Environment.getExternalStorageDirectory() + "/Download/";
        File createFill = new File(fillLocation);
        if (!createFill.exists()) {
            createFill.mkdirs();
        }

        String filepath = fillLocation + "/" + "App Name " + Calendar.getInstance().getTime().toString() + ".jpg";
        File fileScreenShot = new File(filepath);

        FileOutputStream fileOutputStream = null;
        try {
            Toast.makeText(context, "Saved - " + filepath, Toast.LENGTH_SHORT).show();
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

    //resize bitmap image
    public Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int maxSize =1000;
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    //data store background image
    public void getCurrentBackground(int imagePosition) {
        SharedPreferences store = context.getSharedPreferences("imagePosition", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        editor.putInt("imagePosition", imagePosition);
        editor.commit();
    }

    public int setCurrentBackground() {
        SharedPreferences store = context.getSharedPreferences("imagePosition", Context.MODE_PRIVATE);
        return store.getInt("imagePosition", 0);
    }
    //data store background image
    public void getCurrentFont(int fontPosition) {
        SharedPreferences store = context.getSharedPreferences("fontPosition", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        editor.putInt("fontPosition", fontPosition);
        editor.commit();
    }

    public int setCurrentFont() {
        SharedPreferences store = context.getSharedPreferences("fontPosition", Context.MODE_PRIVATE);
        return store.getInt("fontPosition", 0);
    }

}
