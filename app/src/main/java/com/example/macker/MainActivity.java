package com.example.macker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int IMAGE_REQUEST = 1;
    SpacingClass spacingClass;
    EasyAccess easyAccess;
    Point p;
    Bitmap bitmap;
    String[] filterColorCode, filterColorName;
    Uri imageUri;
    ImageFilter imageFilter;
    LinearLayout canvasBackground, backgroundList,galleryOpen;
    float letterSpacingNumber = .02f, lineSpacingNumber = 1f;
    //background
    ImageView createBack, createShare, createWallpaper, createDownload, backgroundBack, canvasBackgroundImage, fakeImageView, flip1, flip2;
    //main canvas
    TextView mainTextId, mainSubText, firstComma, secondComma, textBold, textItalic, textBoldItalic, letterSpaceMinus, letterSpaceCount, letterSpacePlus, lineSpaceMinus, lineSpaceCount, lineSpacePlus;
    RelativeLayout saveScreen, reSizeCanvas;
    //control section
    LinearLayout mainControl;
    CardView pictureListId, textEditId, textSizeId, colorId, propertyId, fontListId, shadowId, filterId, effectId;
    ColorSeekBar textColorSeekBar, backgroundColorSeekBar, shadowColor, customizeColor;
    SeekBar fontSizeSeekBar, backgroundColorOpacitySeekBar, shadowLeftRight, shadowUpDown, shadowBlur, filterSeekBar, customizeWidth, customizeHeight, customizeOpacity, imageBlurSeekBar;
    LinearLayout editLayout, textSizeClose, colorClose, propertyClose, shadowClose, fontClose, filterClose, effectClose;
    ImageView textSizeCloseIcon, colorCloseIcon, propertyCloseIcon, shadowCloseIcon, fontCloseIcon, filterCloseIcon, effectCloseIcon;
    EditText messageEdit, whoSayEdit;
    SwitchMaterial commaSwitch, whoSaySwitch, shadowSwitch;
    // control View
    GridView colorFilterGridView;
    //shadow
    int blur = 4, leftRight = 5, upDown = 5, color = android.R.color.black;
    CardView textSizeLayout, colorLayout, propertyLayout, shadowLayout, fontLayout, filterLayout, effectLayout;
    int position = 0, randomNum, randomPosition, letterSpace, lineSpace;
    float xDown = 0;
    float yDown = 0;
    String[] whatSay = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] whoSay = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    //array
    int[] backgroundImage = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5};
    int[] fontArray = {R.font.font1, R.font.font2, R.font.font3, R.font.font4, R.font.font5, R.font.font6, R.font.font7};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        imageFilter = new ImageFilter(); //filter obj
        easyAccess = new EasyAccess();
        spacingClass = new SpacingClass();

        whiteStatus();//status white
        filterColorCode = getResources().getStringArray(R.array.filterColorCode); //get filter color
        filterColorName = getResources().getStringArray(R.array.filterColorName); //get filter name
        randomNum = new Random().nextInt(whoSay.length - 1);//random number
        backgroundList = findViewById(R.id.backgroundList);//background
        editLayout = findViewById(R.id.editLayout);//edit
        //main canvas
        createBack = findViewById(R.id.createBack);
        createShare = findViewById(R.id.createShare);
        createWallpaper = findViewById(R.id.createWallpaper);
        createDownload = findViewById(R.id.createDownload);

        saveScreen = findViewById(R.id.saveScreen);
        canvasBackgroundImage = findViewById(R.id.canvasBackgroundImage);
        fakeImageView = findViewById(R.id.fakeImageView);
        canvasBackground = findViewById(R.id.canvasBackground);
        reSizeCanvas = findViewById(R.id.reSizeCanvas);
        mainTextId = findViewById(R.id.mainTextId);
        mainSubText = findViewById(R.id.mainSubText);
        firstComma = findViewById(R.id.firstComma);
        secondComma = findViewById(R.id.secondComma);

        //control find
        mainControl = findViewById(R.id.mainControl);
        pictureListId = findViewById(R.id.pictureListId);
        textEditId = findViewById(R.id.textEditId);
        textSizeId = findViewById(R.id.textSizeId);
        colorId = findViewById(R.id.colorId);
        propertyId = findViewById(R.id.propertyId);
        fontListId = findViewById(R.id.fontListId);
        shadowId = findViewById(R.id.shadowId);
        filterId = findViewById(R.id.filterId);
        effectId = findViewById(R.id.effectId);
        //root
        // control view
        textSizeLayout = findViewById(R.id.textSizeLayout);
        colorLayout = findViewById(R.id.colorLayout);
        propertyLayout = findViewById(R.id.propertyLayout);
        shadowLayout = findViewById(R.id.shadowLayout);
        fontLayout = findViewById(R.id.fontLayout);
        filterLayout = findViewById(R.id.filterLayout);
        effectLayout = findViewById(R.id.effectLayout);
        //control listener
        createBack.setOnClickListener(this);
        createShare.setOnClickListener(this);
        createWallpaper.setOnClickListener(this);
        createDownload.setOnClickListener(this);

        pictureListId.setOnClickListener(this);
        textEditId.setOnClickListener(this);
        textSizeId.setOnClickListener(this);
        colorId.setOnClickListener(this);
        propertyId.setOnClickListener(this);
        fontListId.setOnClickListener(this);
        shadowId.setOnClickListener(this);
        filterId.setOnClickListener(this);
        effectId.setOnClickListener(this);

        //control view listener
        //mainTextId.getPaint().setStrokeWidth(2);
        //mainTextId.getPaint().setStyle(Paint.Style.STROKE);
        reSizeCanvas.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    opnEditText();
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX, moveY;
                        moveX = event.getX();
                        moveY = event.getY();

                        float distanceX = moveX - xDown;
                        float distanceY = moveY - yDown;
                        reSizeCanvas.setX(reSizeCanvas.getX() + distanceX);
                        reSizeCanvas.setY(reSizeCanvas.getY() + distanceY);
                        break;
                }

                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


    }

    @Override
    public void onClick(View view) {
        int clickId = view.getId();
        if (clickId == R.id.createBack) {
            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
        } else if (clickId == R.id.createShare) {
            shareImage();
        } else if (clickId == R.id.createWallpaper) {
            Toast.makeText(this, "createWallpaper", Toast.LENGTH_SHORT).show();
        } else if (clickId == R.id.createDownload) {
            easyAccess.singleView(MainActivity.this, "12");
            easyAccess.getImageResource(saveScreen);
            easyAccess.imageSave(true);
        } else if (clickId == R.id.pictureListId) {
            openPictureList();
        } else if (clickId == R.id.backgroundBack) {
            stopAnimation(backgroundList);
        } else if (clickId == R.id.galleryOpenId) {
            openGallery();
        } else if (clickId == R.id.textEditId) {
            opnEditText();
        } else if (clickId == R.id.closeEditText) {
            closeKeyboard();
            stopAnimation(editLayout);
        } else if (clickId == R.id.cleanEditText) {
            messageEdit.setText("");
            whoSayEdit.setText("");
        } else if (clickId == R.id.submitEditText) {
            setText();
        } else if (clickId == R.id.editRandomTxt) {
            setRandomText();
        } else if (clickId == R.id.textSizeId) {
            openTextSize();
        } else if (clickId == R.id.textSizeClose) {
            stopAnimation(textSizeLayout);
        } else if (clickId == R.id.colorId) {
            setColor();
        } else if (clickId == R.id.colorClose) {
            stopAnimation(colorLayout);
        } else if (clickId == R.id.propertyId) {
            setProperty();
        } else if (clickId == R.id.propertyClose) {
            stopAnimation(propertyLayout);
        } else if (clickId == R.id.shadowId) {
            setShadow();
        } else if (clickId == R.id.shadowClose) {
            stopAnimation(shadowLayout);
        } else if (clickId == R.id.fontListId) {
            setFont();
        } else if (clickId == R.id.fontClose) {
            stopAnimation(fontLayout);
        } else if (clickId == R.id.effectId) {
            setEffect();
        } else if (clickId == R.id.effectClose) {
            stopAnimation(effectLayout);
        } else if (clickId == R.id.filterId) {
            setFilter();
        } else if (clickId == R.id.filterClose) {
            stopAnimation(filterLayout);
        } else if (clickId == R.id.filterDisable) {
            bitmap = ((BitmapDrawable) fakeImageView.getDrawable()).getBitmap();
            canvasBackgroundImage.setImageBitmap(bitmap);
        } else if (clickId == R.id.filterSubmit) {
            bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();
            canvasBackgroundImage.setImageBitmap(bitmap);
        } else if (clickId == R.id.textBold) {
            setTextStyle(Typeface.BOLD);
        } else if (clickId == R.id.textItalic) {
            setTextStyle(Typeface.ITALIC);
        } else if (clickId == R.id.textBoldItalic) {
            setTextStyle(Typeface.BOLD_ITALIC);
        } else if (clickId == R.id.flip1) {
            bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();
            canvasBackgroundImage.setImageBitmap(imageFilter.flipImage(bitmap, 1));
        } else if (clickId == R.id.flip2) {
            bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();
            canvasBackgroundImage.setImageBitmap(imageFilter.flipImage(bitmap, 2));
        } else if (clickId == R.id.letterSpaceMinus) {
            letterSpacingNumber = spacingClass.letterSpacing(letterSpacingNumber = (letterSpacingNumber - 0.02f));
            letterSpace = spacingClass.letterSpacingCount(--letterSpace);

            letterAndLineSpacing();
        } else if (clickId == R.id.letterSpacePlus) {
            letterSpacingNumber = spacingClass.letterSpacing(letterSpacingNumber = (letterSpacingNumber + 0.02f));
            letterSpace = spacingClass.letterSpacingCount(++letterSpace);
            letterAndLineSpacing();
        } else if (clickId == R.id.lineSpaceMinus) {
            lineSpacingNumber = spacingClass.lineSpacing(lineSpacingNumber = (lineSpacingNumber - 0.2f));
            lineSpace = spacingClass.lineSpacingCount(--lineSpace);
            letterAndLineSpacing();
        } else if (clickId == R.id.lineSpacePlus) {
            lineSpacingNumber = spacingClass.lineSpacing(lineSpacingNumber = (lineSpacingNumber + 0.2f));
            lineSpace = spacingClass.lineSpacingCount(++lineSpace);
            letterAndLineSpacing();
        }
    }

    private void letterAndLineSpacing() {
        letterSpaceCount.setText(String.valueOf(letterSpace));
        lineSpaceCount.setText(String.valueOf(lineSpace));
        mainTextId.setLetterSpacing(letterSpacingNumber);
        mainSubText.setLetterSpacing(letterSpacingNumber);
        mainTextId.setLineSpacing(0, lineSpacingNumber);

    }

    private void removeView() {
        if (backgroundList.getVisibility() == View.VISIBLE) {
            stopAnimation(backgroundList);
        } else if (editLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(editLayout);
        } else if (textSizeLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(textSizeLayout);
        } else if (colorLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(colorLayout);
        } else if (fontLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(fontLayout);
        } else if (effectLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(effectLayout);
        } else if (filterLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(filterLayout);
        } else if (propertyLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(propertyLayout);
        } else if (shadowLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(shadowLayout);
        }
    }

    private void setTextStyle(int id) {
        mainTextId.setTypeface(null, id);
        mainSubText.setTypeface(null, id);
        firstComma.setTypeface(null, id);
        secondComma.setTypeface(null, id);

    }

    private void setEffect() {
        startAnimation(effectLayout);
        imageBlurSeekBar = findViewById(R.id.imageBlur);
        effectClose = findViewById(R.id.effectClose);
        effectCloseIcon = findViewById(R.id.effectCloseIcon);
        effectClose.setOnClickListener(this);
        bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();

        imageBlurSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvasBackgroundImage.setImageBitmap(imageFilter.blurRenderScript(getApplicationContext(), bitmap, (progress / 4)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setFilter() {
        startAnimation(filterLayout);
        bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();


        ImageView filterDisable = findViewById(R.id.filterDisable);
        ImageView filterSubmit = findViewById(R.id.filterSubmit);
        colorFilterGridView = findViewById(R.id.colorFilterGridView);
        filterClose = findViewById(R.id.filterClose);
        filterCloseIcon = findViewById(R.id.filterCloseIcon);
        filterSeekBar = findViewById(R.id.filterSeekBar);
        filterDisable.setOnClickListener(this);
        filterSubmit.setOnClickListener(this);
        filterClose.setOnClickListener(this);
        FilterAdapter colorFilterAdapter = new FilterAdapter();
        colorFilterGridView.setAdapter(colorFilterAdapter);

        colorFilterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = Color.parseColor(filterColorCode[position]);
                canvasBackgroundImage.setImageBitmap(imageFilter.applyShadingFilter(bitmap, color));
            }
        });

        filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvasBackgroundImage.setImageBitmap(imageFilter.applyTintEffect(bitmap, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void shareImage() {
        int[] location = new int[2];
        createShare.getLocationOnScreen(location);
        p = new Point();
        p.x = location[0];
        p.y = location[1];
        easyAccess.showPopup(MainActivity.this, p, "positionString");
        easyAccess.getImageResource(saveScreen);

    }

    private void setFont() {
        startAnimation(fontLayout);
        FontClass fontClass = new FontClass();
        ListView fontListView = findViewById(R.id.fontListView);
        fontClose = findViewById(R.id.fontClose);
        fontCloseIcon = findViewById(R.id.fontCloseIcon);
        fontClose.setOnClickListener(this);
        fontListView.setAdapter(fontClass);

        fontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), fontArray[position]);
                mainTextId.setTypeface(typeface);
            }
        });

    }

    private void setShadow() {
        startAnimation(shadowLayout);
        shadowSwitch = findViewById(R.id.shadowSwitch);
        shadowColor = findViewById(R.id.shadowColor);
        shadowLeftRight = findViewById(R.id.shadowLeftRight);
        shadowUpDown = findViewById(R.id.shadowUpDown);
        shadowBlur = findViewById(R.id.shadowBlur);
        shadowClose = findViewById(R.id.shadowClose);
        shadowCloseIcon = findViewById(R.id.shadowCloseIcon);
        shadowClose.setOnClickListener(this);
        if (shadowLeftRight.getProgress() == 0) {
            shadowLeftRight.setProgress(shadowLeftRight.getMax() / 2);
        }
        if (shadowUpDown.getProgress() == 0) {
            shadowUpDown.setProgress(shadowUpDown.getMax() / 2);
        }
        if (shadowBlur.getProgress() == 0) {
            shadowBlur.setProgress(4);
        }
        shadowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean shadowSwitch) {
                if (shadowSwitch) {
                    setShadow(blur, leftRight, upDown, color);
                } else {
                    mainTextId.setShadowLayer(0, leftRight, upDown, color);
                    mainSubText.setShadowLayer(0, leftRight, upDown, color);
                    firstComma.setShadowLayer(0, leftRight, upDown, color);
                    secondComma.setShadowLayer(0, leftRight, upDown, color);
                }
            }
        });
        shadowColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {

            @Override
            public void onColorChangeListener(int i) {
                color = i;
                setShadow(blur, leftRight, upDown, color);
            }
        });
        shadowLeftRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int upDate;
                if (i < 10) {
                    upDate = (10 - i);
                    leftRight = upDate;
                } else if (i > 10) {
                    upDate = -(10 - i);
                    leftRight = -upDate;
                }
                setShadow(blur, leftRight, upDown, color);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        shadowUpDown.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int upDate;
                if (i < 10) {
                    upDate = (10 - i);
                    upDown = upDate;
                } else if (i > 10) {
                    upDate = -(10 - i);
                    upDown = -upDate;
                }
                setShadow(blur, leftRight, upDown, color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        shadowBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                blur = i / 2;
                setShadow(blur, leftRight, upDown, color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setShadow(float mBlur, int mLeftRight, int mUpDown, int mColor) {
        shadowSwitch.setChecked(true);
        mainTextId.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        mainSubText.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        firstComma.setShadowLayer(mBlur, 0, 0, mColor);
        secondComma.setShadowLayer(mBlur, 0, 0, mColor);

    }

    private void setProperty() {
        startAnimation(propertyLayout);

        textBold = findViewById(R.id.textBold);
        textItalic = findViewById(R.id.textItalic);
        textBoldItalic = findViewById(R.id.textBoldItalic);

        flip1 = findViewById(R.id.flip1);
        flip2 = findViewById(R.id.flip2);

        letterSpaceMinus = findViewById(R.id.letterSpaceMinus);
        letterSpaceCount = findViewById(R.id.letterSpaceCount);
        letterSpacePlus = findViewById(R.id.letterSpacePlus);

        lineSpaceMinus = findViewById(R.id.lineSpaceMinus);
        lineSpaceCount = findViewById(R.id.lineSpaceCount);
        lineSpacePlus = findViewById(R.id.lineSpacePlus);

        customizeWidth = findViewById(R.id.customizeWidth);
        customizeHeight = findViewById(R.id.customizeHeight);
        customizeColor = findViewById(R.id.customizeColor);
        customizeOpacity = findViewById(R.id.customizeOpacity);

        commaSwitch = findViewById(R.id.commaSwitch);
        whoSaySwitch = findViewById(R.id.whoSaySwitch);
        propertyClose = findViewById(R.id.propertyClose);
        propertyCloseIcon = findViewById(R.id.propertyCloseIcon);

        textBold.setOnClickListener(this);
        textItalic.setOnClickListener(this);
        textBoldItalic.setOnClickListener(this);
        flip1.setOnClickListener(this);
        flip2.setOnClickListener(this);

        letterSpaceMinus.setOnClickListener(this);
        letterSpacePlus.setOnClickListener(this);
        lineSpaceMinus.setOnClickListener(this);
        lineSpacePlus.setOnClickListener(this);

        propertyClose.setOnClickListener(this);


        customizeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setMargins(progress, customizeHeight.getProgress(), progress, customizeHeight.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        customizeHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setMargins(customizeWidth.getProgress(), progress, customizeWidth.getProgress(), progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        customizeColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                reSizeCanvas.setBackgroundColor(color);
                reSizeCanvas.getBackground().setAlpha(customizeOpacity.getProgress());
            }
        });
        customizeOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int opacity, boolean fromUser) {
                reSizeCanvas.getBackground().setAlpha(opacity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        commaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchB) {
                if (switchB) {
                    firstComma.setVisibility(View.VISIBLE);
                    secondComma.setVisibility(View.VISIBLE);
                } else {
                    firstComma.setVisibility(View.INVISIBLE);
                    secondComma.setVisibility(View.INVISIBLE);
                }
            }
        });
        whoSaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchB) {
                if (switchB) {
                    mainSubText.setVisibility(View.VISIBLE);
                } else {

                    mainSubText.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    public void setMargins(int l, int t, int r, int b) {
        if (reSizeCanvas.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) reSizeCanvas.getLayoutParams();
            p.setMargins(l, t, r, b);
            reSizeCanvas.requestLayout();
        }
    }

    private void setColor() {
        startAnimation(colorLayout);
        textColorSeekBar = findViewById(R.id.textColorSeekBar);
        backgroundColorSeekBar = findViewById(R.id.backgroundColorSeekBar);
        backgroundColorOpacitySeekBar = findViewById(R.id.backgroundColorOpacitySeekBar);
        colorClose = findViewById(R.id.colorClose);
        colorCloseIcon = findViewById(R.id.colorCloseIcon);
        colorClose.setOnClickListener(this);
        textColorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                mainTextId.setTextColor(color);
                mainSubText.setTextColor(color);
                firstComma.setTextColor(color);
                secondComma.setTextColor(color);
            }
        });
        backgroundColorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                canvasBackground.setBackgroundColor(color);
                canvasBackground.getBackground().setAlpha(backgroundColorOpacitySeekBar.getProgress());
            }
        });
        backgroundColorOpacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int opacity, boolean b) {
                canvasBackground.getBackground().setAlpha(opacity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void openTextSize() {
        startAnimation(textSizeLayout);
        fontSizeSeekBar = findViewById(R.id.textSizeSeekBar);
        textSizeClose = findViewById(R.id.textSizeClose);
        textSizeCloseIcon = findViewById(R.id.textSizeCloseIcon);
        textSizeClose.setOnClickListener(this);
        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                fontSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText() {
        closeKeyboard();
        String mainText = messageEdit.getText().toString();
        String whoSay = whoSayEdit.getText().toString();
        if (mainText.isEmpty()) {
            Toast.makeText(this, "Please Write Something", Toast.LENGTH_SHORT).show();
        } else {
            mainTextId.setText(mainText);
            stopAnimation(editLayout);
            closeKeyboard();
        }
        if (!whoSay.isEmpty()) {
            mainSubText.setText("- " + whoSay);
        } else {
            mainSubText.setText("");
        }
    }

    private void fontSize(int fontSize) {
        mainTextId.setTextSize(fontSize);
        mainSubText.setTextSize(fontSize - 5);
        firstComma.setTextSize(fontSize + 5);
        secondComma.setTextSize(fontSize + 4);

    }

    private void opnEditText() {
        startAnimation(editLayout);
        ImageView closeEditText, cleanEditText, submitEditText, editRandomTxt;
        closeEditText = findViewById(R.id.closeEditText);
        cleanEditText = findViewById(R.id.cleanEditText);
        submitEditText = findViewById(R.id.submitEditText);
        editRandomTxt = findViewById(R.id.editRandomTxt);
        messageEdit = findViewById(R.id.messageEditText);
        whoSayEdit = findViewById(R.id.whoSayId);
        messageEdit.setText(mainTextId.getText().toString());
        if (!whoSayEdit.getText().toString().isEmpty()) {
            whoSayEdit.setText(mainSubText.getText().toString().substring(2, mainSubText.length()));
        }

        closeEditText.setOnClickListener(this);
        cleanEditText.setOnClickListener(this);
        submitEditText.setOnClickListener(this);
        editRandomTxt.setOnClickListener(this);
    }

    private void setRandomText() {
        position++;
        randomPosition = randomNum + position;
        if (randomPosition <= whatSay.length - 1) {
            messageEdit.setText(whatSay[(randomPosition)]);
            whoSayEdit.setText(whoSay[randomPosition]);
        } else if (randomPosition >= whatSay.length) {
            messageEdit.setText(whatSay[5]);
            whoSayEdit.setText(whoSay[5]);
            position = -1;
            randomNum = 0;

        }

        //messageEdit.setText(whatSay[position]);
        //whoSayEdit.setText(whoSay[position]);
    }

    private void openGallery() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 21) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                stopAnimation(backgroundList);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                canvasBackgroundImage.setImageBitmap(bitmap);
                fakeImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openPictureList() {
        startAnimation(backgroundList);
        GridView PictureListGridView = findViewById(R.id.backgroundGridView);
        backgroundBack = findViewById(R.id.backgroundBack);
        galleryOpen = findViewById(R.id.galleryOpenId);
        backgroundBack.setOnClickListener(this);
        galleryOpen.setOnClickListener(this);
        SelectBackgroundClass selectBackgroundClass = new SelectBackgroundClass();
        PictureListGridView.setAdapter(selectBackgroundClass);
        PictureListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                canvasBackgroundImage.setImageResource(backgroundImage[position]);
                fakeImageView.setImageResource(backgroundImage[position]);
                stopAnimation(backgroundList);
            }
        });
    }

    private void startAnimation(View view) {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_down_top);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
        mainControl.setVisibility(View.INVISIBLE);


    }

    private void stopAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_down);
        view.startAnimation(animation);
        view.setVisibility(View.INVISIBLE);
        mainControl.setVisibility(View.VISIBLE);

    }

    private void whiteStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));// set status background white
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    class FontClass extends BaseAdapter {

        @Override
        public int getCount() {
            return fontArray.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.font_flow_layout, parent, false);
            }
            TextView fontCount, fontList;
            fontCount = convertView.findViewById(R.id.fontCount);
            fontList = convertView.findViewById(R.id.fontList);
            fontCount.setText(Integer.toString(position));
            return convertView;
        }
    }

    class FilterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return filterColorCode.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.filter_layout, parent, false);
            }
            ImageView imageView = convertView.findViewById(R.id.flowFilterImage);
            TextView textView = convertView.findViewById(R.id.flowFilterName);
            bitmap = ((BitmapDrawable) fakeImageView.getDrawable()).getBitmap();
            imageView.setImageBitmap(bitmap);
            int color = Color.parseColor(filterColorCode[position]);
            textView.setBackgroundColor(color);
            if (position == 5 || position == 19 || position == 37 || position == 41 || position == 43 || position == 54 || position == 83 || position == 84) {
                textView.setTextColor(Color.BLACK);
            }
            textView.setText(filterColorName[position]);


            return convertView;
        }
    }

    class SelectBackgroundClass extends BaseAdapter {

        @Override
        public int getCount() {
            return backgroundImage.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.background_flow_layout, parent, false);
            }
            ImageView imageView = convertView.findViewById(R.id.flowImageView);
            imageView.setImageResource(backgroundImage[position]);

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeView();
    }
}

class SpacingClass {
    public float letterSpacing(float space) {
        if (space < -0.10) {
            space = -0.10f;
            return space;
        } else if (space > 1) {
            space = 1;
            return space;
        } else {
            return space;
        }
    }

    public int letterSpacingCount(int count) {
        if (count < -5) {
            count = -5;
            return count;
        } else if (count > 50) {
            count = 50;
            return count;
        } else {
            return count;
        }
    }


    public float lineSpacing(float space) {
        if (space < 0.2) {
            space = 0.2f;
            return space;
        } else if (space > 10) {
            space = 10;
            return space;
        } else {
            return space;
        }
    }

    public int lineSpacingCount(int count) {
        if (count < -5) {
            count = -5;
            return count;
        } else if (count > 45) {
            count = 45;
            return count;
        } else {
            return count;
        }
    }
}