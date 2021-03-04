package com.example.macker;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class EasyAccess {
    Context context;
    private String positionString;
    private RelativeLayout screen;

    //default context,position sting
    public void singleView(Context context, String positionString) {
        this.context = context;
        this.positionString = positionString;
    }

    //image resource get
    public void getImageResource(RelativeLayout screen) {
        this.screen = screen;
    }

    //copy method
    public void copy() {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("key", positionString);
        clipboardManager.setPrimaryClip(clipData);
        //Toast.makeText(context, "Coped "+positionString, Toast.LENGTH_SHORT).show();
    }

    //share method
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, positionString);
        context.startActivity(Intent.createChooser(intent, "Send to"));
    }

    //fav method
    public void favItem() {
        Toast.makeText(context, "Fav", Toast.LENGTH_SHORT).show();
    }

    //popup
    public void showPopup(final Activity context, Point p, final String positionString) {
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.image_share_popup, viewGroup);

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(450);
        popup.setHeight(380);
        popup.setFocusable(true);

        int OFFSET_X = 50;
        int OFFSET_Y = 0;

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);


        TextView textShare = layout.findViewById(R.id.shareText);

        textShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share text", Toast.LENGTH_SHORT).show();
                singleView(context, positionString);
                share();
            }
        });
        TextView imageShare = layout.findViewById(R.id.shareImage);
        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSave(true);
            }
        });
    }

    //image save or share
    public void imageSave(boolean imageShare) {
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
