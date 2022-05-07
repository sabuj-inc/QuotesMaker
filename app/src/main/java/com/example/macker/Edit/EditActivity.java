package com.example.macker.Edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
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
import android.widget.PopupWindow;
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
import com.example.macker.EditAccess;
import com.example.macker.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.IOException;
import java.util.Random;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CANVAS_IMAGE_REQUEST = 0, IMAGE_REQUEST1 = 1, IMAGE_REQUEST2 = 2, IMAGE_REQUEST3 = 3;
    SpacingClass spacingClass;
    EditAccess easyAccess;
    Point p;
    Bitmap bitmap;
    Uri imageUri;
    ImageFilter imageFilter;
    LinearLayout canvasBackground, backgroundList;
    int bold = 0, italic = 0, boldItalic = 0;

    GradientBackgroundClass gradientBackgroundClass = new GradientBackgroundClass();
    //gradient color
    RelativeLayout galleryOpen;
    GridView GradientGridView;
    LinearLayout mainGradientCreate, mainColorText;
    SeekBar solidOpacitySeekBar;
    GradientDrawable constanceGradient, randomGradient;
    int[] randomColorList = {0, 0, 0}, gradientColorList = {0, 0, 0};
    RelativeLayout colorBackground, colorText;
    ShapeableImageView waterImage1, waterImage2, waterImage3, resultImage1, resultImage2, resultImage3;
    TextWatcher textWatcher = null;
    //main canvas
    TextView mainTextId, mainSubText, firstComma, secondComma, textBold, textItalic, textBoldItalic, letterSpaceMinus,
            letterSpaceCount, letterSpacePlus, lineSpaceMinus, lineSpaceCount, lineSpacePlus, backgroundColorBar, textColorBar,
            watermarkText;
    RelativeLayout saveScreen, reSizeCanvas, contentLayout;
    //control section
    LinearLayout mainControl, watermarkGroup;
    ColorSeekBar textColorSeekBar, backgroundColorSeekBar, shadowColor, customizeColor, watermarkColor, iconStackColor;
    SeekBar fontSizeSeekBar, shadowLeftRight, shadowUpDown, shadowBlur, customizeWidth, customizeHeight, customizeOpacity, imageBlurSeekBar, watermarkTextSize, watermarkIconSize, watermarkOpacity;
    LinearLayout editLayout, textSizeClose, colorClose, propertyClose, shadowClose, fontClose, watermarkClose;
    ImageView textSizeCloseIcon, colorCloseIcon, propertyCloseIcon, shadowCloseIcon, fontCloseIcon,
            createBack, createShare, createDownload, backgroundBack, canvasBackgroundImage, fakeImageView, flip1, flip2,
            watermarkCloseIcon;
    EditText messageEdit, author, watermarkEdit;
    SwitchMaterial commaSwitch, whoSaySwitch, shadowSwitch, textShadowSwitch, watermarkSwitch;
    // control View
    GridView pictureListGridView, watermarkFontGridView;
    CardView pictureListId, textEditId, textSizeId, colorId, propertyId, fontListId, shadowId, watermarkId, textSizeLayout, colorLayout, propertyLayout, shadowLayout, watermarkLayout, fontLayout;
    int blur = 4, leftRight = 5, upDown = 5, color = Color.BLACK, letterSpace = 0, lineSpace = 0;
    float xDown = 0, yDown = 0, letterSpacingNumber = .02f, lineSpacingNumber = 1f;

    //array
    int[] backgroundImage = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5, R.drawable.bg6,
            R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10, R.drawable.bg11, R.drawable.bg12,
            R.drawable.bg13, R.drawable.bg14, R.drawable.bg15, R.drawable.bg16, R.drawable.bg17, R.drawable.bg18,
            R.drawable.bg19};
    int[] fontArray = {R.font.font1, R.font.font2, R.font.font3, R.font.font4, R.font.font5, R.font.font6, R.font.font7,
            R.font.font8, R.font.font9, R.font.font10, R.font.font11, R.font.font12, R.font.font13, R.font.font14,
            R.font.font15, R.font.font16, R.font.font17, R.font.font18, R.font.font19, R.font.font20, R.font.font21,
            R.font.font22, R.font.font23, R.font.font24, R.font.font25, R.font.font26, R.font.font27, R.font.font28,
            R.font.font29, R.font.font30, R.font.font31, R.font.font32, R.font.font33, R.font.font34, R.font.font35,
            R.font.font36, R.font.font37, R.font.font38, R.font.font39, R.font.font40, R.font.font41, R.font.font42
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        imageFilter = new ImageFilter(); //filter obj
        easyAccess = new EditAccess(this);
        spacingClass = new SpacingClass();

        whiteStatus();//status white

        backgroundList = findViewById(R.id.backgroundList);//background
        editLayout = findViewById(R.id.editLayout);//edit

        //main canvas
        shadowSwitch = findViewById(R.id.shadowSwitch);//for perfect work shadow switch
        createBack = findViewById(R.id.createBack);
        createShare = findViewById(R.id.createShare);
        createDownload = findViewById(R.id.createDownload);

        saveScreen = findViewById(R.id.saveScreen);
        canvasBackgroundImage = findViewById(R.id.canvasBackgroundImage);
        fakeImageView = findViewById(R.id.fakeImageView);
        canvasBackground = findViewById(R.id.canvasBackground);
        reSizeCanvas = findViewById(R.id.reSizeCanvas);
        contentLayout = findViewById(R.id.contentLayout);
        mainTextId = findViewById(R.id.mainTextId);
        mainSubText = findViewById(R.id.mainSubText);
        firstComma = findViewById(R.id.firstComma);
        secondComma = findViewById(R.id.secondComma);

        //watermark find
        watermarkGroup = findViewById(R.id.watermarkGroup);
        resultImage1 = findViewById(R.id.resultImage1);
        resultImage2 = findViewById(R.id.resultImage2);
        resultImage3 = findViewById(R.id.resultImage3);
        watermarkText = findViewById(R.id.watermarkText);

        //control find
        mainControl = findViewById(R.id.mainControl);
        pictureListId = findViewById(R.id.pictureListId);
        textEditId = findViewById(R.id.textEditId);
        textSizeId = findViewById(R.id.textSizeId);
        colorId = findViewById(R.id.colorId);
        propertyId = findViewById(R.id.propertyId);
        fontListId = findViewById(R.id.fontListId);
        shadowId = findViewById(R.id.shadowId);
        watermarkId = findViewById(R.id.watermarkId);
        //root
        // control view
        textSizeLayout = findViewById(R.id.textSizeLayout);
        colorLayout = findViewById(R.id.colorLayout);
        propertyLayout = findViewById(R.id.propertyLayout);
        fontLayout = findViewById(R.id.fontLayout);
        shadowLayout = findViewById(R.id.shadowLayout);
        watermarkLayout = findViewById(R.id.watermarkLayout);
        //control listener
        createBack.setOnClickListener(this);
        createShare.setOnClickListener(this);
        createDownload.setOnClickListener(this);

        pictureListId.setOnClickListener(this);
        textEditId.setOnClickListener(this);
        textSizeId.setOnClickListener(this);
        colorId.setOnClickListener(this);
        propertyId.setOnClickListener(this);
        fontListId.setOnClickListener(this);
        shadowId.setOnClickListener(this);
        watermarkId.setOnClickListener(this);

        //random load background image
        easyAccess.getCurrentBackground(easyAccess.setCurrentBackground() + 1);

        if (easyAccess.setCurrentBackground() < backgroundImage.length) {
            canvasBackgroundImage.setImageResource(backgroundImage[easyAccess.setCurrentBackground()]);
            canvasBackground.getBackground().setAlpha(100);
        } else {
            easyAccess.getCurrentBackground(0);
        }
        //random load font
        easyAccess.getCurrentFont(easyAccess.setCurrentFont() + 1);
        if (easyAccess.setCurrentFont() < fontArray.length) {
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), fontArray[easyAccess.setCurrentFont()]);
            mainTextId.setTypeface(typeface);
            mainSubText.setTypeface(typeface);
        } else {
            easyAccess.getCurrentFont(0);
        }

        //control view listener
        //mainTextId.getPaint().setStrokeWidth(2);
        //mainTextId.getPaint().setStyle(Paint.Style.STROKE);

        watermarkGroup.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (isOpen()) {
                        openWatermark();
                    }

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (isOpen()) {
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
                            watermarkGroup.setX(watermarkGroup.getX() + distanceX);
                            watermarkGroup.setY(watermarkGroup.getY() + distanceY);
                            break;
                    }
                }

                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        contentLayout.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {

                    if (isOpen()) {
                        opnEditText();
                    }

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (isOpen()) {
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
                }

                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        reSizeCanvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (isOpen()) {
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


                            reSizeCanvas.setY(reSizeCanvas.getY() + distanceY);
                            reSizeCanvas.setX(reSizeCanvas.getX() + distanceX);

                            Log.d("X ", String.valueOf(reSizeCanvas.getX() + distanceX));
                            Log.d("y ", String.valueOf(reSizeCanvas.getY() + distanceY));
                            break;
                    }
                }

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
            easyAccess.imageSave(saveScreen, true);
        } else if (clickId == R.id.createDownload) {
            easyAccess.imageSave(saveScreen, false);
        } else if (clickId == R.id.pictureListId) {
            openPictureList();
        } else if (clickId == R.id.backgroundBack) {
            stopAnimation(backgroundList);
        } else if (clickId == R.id.galleryOpenId) {
            openGallery(CANVAS_IMAGE_REQUEST);
        } else if (clickId == R.id.textEditId) {
            opnEditText();
        } else if (clickId == R.id.closeEditText) {
            closeKeyboard();
            stopAnimation(editLayout);
        } else if (clickId == R.id.cleanEditText) {
            messageEdit.setText("");
            author.setText("");
        } else if (clickId == R.id.submitEditText) {
            setText();
        } else if (clickId == R.id.textSizeId) {
            openTextSize();
        } else if (clickId == R.id.textSizeClose) {
            stopAnimation(textSizeLayout);
        } else if (clickId == R.id.colorId) { //color gradient
            setColor();
        } else if (clickId == R.id.colorBackground) {
            backgroundColorBar.setVisibility(View.VISIBLE);
            mainGradientCreate.setVisibility(View.VISIBLE);
            textColorBar.setVisibility(View.INVISIBLE);
            mainColorText.setVisibility(View.INVISIBLE);

        } else if (clickId == R.id.colorText) {
            textColorBar.setVisibility(View.VISIBLE);
            mainColorText.setVisibility(View.VISIBLE);
            backgroundColorBar.setVisibility(View.INVISIBLE);
            mainGradientCreate.setVisibility(View.INVISIBLE);
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
        } else if (clickId == R.id.watermarkId) {
            openWatermark();
        } else if (clickId == R.id.watermarkClose) {
            stopAnimation(watermarkLayout);
        } else if (clickId == R.id.fontListId) {
            setFont();
        } else if (clickId == R.id.fontClose) {
            stopAnimation(fontLayout);
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
        if (clickId == R.id.waterImage1) {
            showWatermarkPopup(IMAGE_REQUEST1);
        } else if (clickId == R.id.waterImage2) {
            showWatermarkPopup(IMAGE_REQUEST2);
        } else if (clickId == R.id.waterImage3) {
            showWatermarkPopup(IMAGE_REQUEST3);
        }
    }

    /*-------------------------------------watermark area-------------------------------*/
    private void openWatermark() {
        startAnimation(watermarkLayout);
        //find watermark
        watermarkGroup = findViewById(R.id.watermarkGroup);
        watermarkSwitch = findViewById(R.id.watermarkSwitch);
        waterImage1 = findViewById(R.id.waterImage1);
        waterImage2 = findViewById(R.id.waterImage2);
        waterImage3 = findViewById(R.id.waterImage3);
        watermarkEdit = findViewById(R.id.watermarkEdit);
        watermarkFontGridView = findViewById(R.id.watermarkFontGridView);
        watermarkTextSize = findViewById(R.id.watermarkTextSize);
        watermarkIconSize = findViewById(R.id.watermarkIconSize);
        watermarkColor = findViewById(R.id.watermarkColor);
        iconStackColor = findViewById(R.id.iconStackColor);
        watermarkOpacity = findViewById(R.id.watermarkOpacity);
        watermarkClose = findViewById(R.id.watermarkClose);
        watermarkCloseIcon = findViewById(R.id.watermarkCloseIcon);

        //Listener
        waterImage1.setOnClickListener(this);
        waterImage2.setOnClickListener(this);
        waterImage3.setOnClickListener(this);
        watermarkClose.setOnClickListener(this);


        //visible or invisible
        watermarkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    watermarkGroup.setVisibility(View.VISIBLE);
                } else {
                    watermarkGroup.setVisibility(View.INVISIBLE);
                }
            }
        });


        // add run time text
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                watermarkText.setText(watermarkEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        watermarkEdit.addTextChangedListener(textWatcher);

        final WatermarkFontAdapter watermarkFontAdapter = new WatermarkFontAdapter(this, fontArray);
        watermarkFontGridView.setAdapter(watermarkFontAdapter);
        watermarkFontGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    watermarkText.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
                    watermarkEdit.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

                } else {
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), fontArray[position - 1]);
                    watermarkEdit.setTypeface(typeface);
                    watermarkText.setTypeface(typeface);
                }
            }
        });


        watermarkTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                watermarkText.setTextSize(progress / 4);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        watermarkIconSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultImage1.getLayoutParams().height = progress;
                resultImage1.getLayoutParams().width = progress;
                resultImage1.requestLayout();

                resultImage2.getLayoutParams().height = progress;
                resultImage2.getLayoutParams().width = progress;
                resultImage2.requestLayout();

                resultImage3.getLayoutParams().height = progress;
                resultImage3.getLayoutParams().width = progress;
                resultImage3.requestLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        watermarkColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                watermarkText.setTextColor(color);
                watermarkEdit.setTextColor(color);

            }
        });
        iconStackColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                resultImage1.setStrokeColor(ColorStateList.valueOf(color));
                resultImage2.setStrokeColor(ColorStateList.valueOf(color));
                resultImage3.setStrokeColor(ColorStateList.valueOf(color));
            }
        });
        watermarkOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float opacity = watermarkOpacity.getProgress() / 20f;
                //Toast.makeText(EditActivity.this, ""+opacity, Toast.LENGTH_SHORT).show();
                if (opacity > 0.05) {
                    watermarkText.setAlpha(opacity);
                    resultImage1.setAlpha(opacity);
                    resultImage2.setAlpha(opacity);
                    resultImage3.setAlpha(opacity);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //change watermark icon
    public void showWatermarkPopup(final int IMAGE_REQUEST) {
        int[] location = new int[2];
        waterImage1.getLocationOnScreen(location);
        p = new Point();
        p.x = location[0];
        p.y = location[1];
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.watermarkPopup);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.watermark_image_upload, viewGroup);

        final PopupWindow popup = new PopupWindow(this);
        popup.setContentView(layout);
        popup.setWidth(450);
        popup.setHeight(380);
        popup.setFocusable(true);

        int OFFSET_X = -100;
        int OFFSET_Y = -300;

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);


        TextView uploadNew = layout.findViewById(R.id.uploadNewImage);
        TextView remove = layout.findViewById(R.id.removeImage);

        uploadNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(IMAGE_REQUEST);
                popup.dismiss();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IMAGE_REQUEST == 1) {
                    waterImage1.setImageResource(R.drawable.gradient_plus_icon);
                    resultImage1.setVisibility(View.GONE);
                } else if (IMAGE_REQUEST == 2) {
                    waterImage2.setImageResource(R.drawable.gradient_plus_icon);
                    resultImage2.setVisibility(View.GONE);
                } else if (IMAGE_REQUEST == 3) {
                    waterImage3.setImageResource(R.drawable.gradient_plus_icon);
                    resultImage3.setVisibility(View.GONE);
                }
                popup.dismiss();
            }
        });
    }

    private void letterAndLineSpacing() {
        letterSpaceCount.setText(String.valueOf(letterSpace));
        lineSpaceCount.setText(String.valueOf(lineSpace));
        mainTextId.setLetterSpacing(letterSpacingNumber);
        mainSubText.setLetterSpacing(letterSpacingNumber);
        mainTextId.setLineSpacing(0, lineSpacingNumber);

    }

    private void setTextStyle(int id) {
        mainTextId.setTypeface(null, id);
        mainSubText.setTypeface(null, id);
        firstComma.setTypeface(null, id);
        secondComma.setTypeface(null, id);
        int draw = R.drawable.text_style_background, color = Color.TRANSPARENT;
        if (id == 1) {
            textItalic.setBackgroundColor(color);
            textBoldItalic.setBackgroundColor(color);
            italic = 0;
            boldItalic = 0;
            if (bold == 0) {
                textBold.setBackground(getDrawable(draw));
                bold = 1;
            } else {
                setTextStyle(0);
                textBold.setBackgroundColor(color);
                bold = 0;
            }
        } else if (id == 2) {
            textBold.setBackgroundColor(color);
            textBoldItalic.setBackgroundColor(color);
            bold = 0;
            boldItalic = 0;
            if (italic == 0) {
                textItalic.setBackground(getDrawable(draw));
                italic = 1;
            } else {
                setTextStyle(0);
                textItalic.setBackgroundColor(color);
                italic = 0;
            }
        } else if (id == 3) {
            textBold.setBackgroundColor(color);
            textItalic.setBackgroundColor(color);
            bold = 0;
            italic = 0;
            if (boldItalic == 0) {
                textBoldItalic.setBackground(getDrawable(draw));
                boldItalic = 1;
            } else {
                setTextStyle(0);
                textBoldItalic.setBackgroundColor(color);
                boldItalic = 0;
            }
        }
    }


    private boolean isOpen() {
        if (backgroundList.getVisibility() != View.VISIBLE && editLayout.getVisibility() != View.VISIBLE && textSizeLayout.getVisibility() != View.VISIBLE && colorLayout.getVisibility() != View.VISIBLE && fontLayout.getVisibility() != View.VISIBLE &&
                propertyLayout.getVisibility() != View.VISIBLE && shadowLayout.getVisibility() != View.VISIBLE && watermarkLayout.getVisibility() != View.VISIBLE) {
            return true;
        }
        return false;
    }

    private void setFont() {
        startAnimation(fontLayout);
        FontClass fontClass = new FontClass();
        GridView fontListView = findViewById(R.id.fontGridView);
        fontClose = findViewById(R.id.fontClose);
        fontCloseIcon = findViewById(R.id.fontCloseIcon);
        fontClose.setOnClickListener(this);
        fontListView.setAdapter(fontClass);

        fontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), fontArray[position]);
                mainTextId.setTypeface(typeface);
                mainSubText.setTypeface(typeface);
            }
        });

    }

    private void setShadow() {
        startAnimation(shadowLayout);
        //shadowSwitch = findViewById(R.id.shadowSwitch);
        shadowColor = findViewById(R.id.shadowColor);
        shadowLeftRight = findViewById(R.id.shadowLeftRight);
        shadowUpDown = findViewById(R.id.shadowUpDown);
        shadowBlur = findViewById(R.id.shadowBlur);
        shadowClose = findViewById(R.id.shadowClose);
        shadowCloseIcon = findViewById(R.id.shadowCloseIcon);
        setShadow(blur, leftRight, upDown, color);
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
        //blur image
        imageBlurSeekBar = findViewById(R.id.imageBlur);
        bitmap = ((BitmapDrawable) canvasBackgroundImage.getDrawable()).getBitmap();

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
                if (customizeOpacity.getProgress() < 15) {
                    reSizeCanvas.setBackgroundColor(Color.parseColor("#9A222020"));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                reSizeCanvas.setBackgroundColor(customizeColor.getColor());
                reSizeCanvas.getBackground().setAlpha(customizeOpacity.getProgress());

            }
        });
        customizeHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setMargins(customizeWidth.getProgress(), progress, customizeWidth.getProgress(), progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (customizeOpacity.getProgress() < 15) {
                    reSizeCanvas.setBackgroundColor(Color.parseColor("#9A222020"));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                reSizeCanvas.setBackgroundColor(customizeColor.getColor());
                reSizeCanvas.getBackground().setAlpha(customizeOpacity.getProgress());
            }
        });
        customizeColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                reSizeCanvas.setBackgroundColor(color);
                reSizeCanvas.getBackground().setAlpha(customizeOpacity.getProgress());
                if (customizeOpacity.getProgress() < 15) {
                    customizeOpacity.setProgress(15);
                }
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


    //<-----------------------------------------------------color section---------------------------------->
    private void setColor() {
        startAnimation(colorLayout);

        //gradient
        colorBackground = findViewById(R.id.colorBackground);
        colorText = findViewById(R.id.colorText);
        backgroundColorBar = findViewById(R.id.backgroundColorBar);
        solidOpacitySeekBar = findViewById(R.id.solidOpacitySeekBar);
        textColorBar = findViewById(R.id.textColorBar);
        mainGradientCreate = findViewById(R.id.mainGradientCreate);
        mainColorText = findViewById(R.id.mainColorText);
        backgroundColorSeekBar = findViewById(R.id.backgroundColorSeekBar);

        GradientGridView = findViewById(R.id.GradientGridView);

        colorBackground.setOnClickListener(this);
        colorText.setOnClickListener(this);

        GradientGridView.setAdapter(gradientBackgroundClass);//constance gradient

        backgroundColorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                canvasBackground.setBackgroundColor(color);
                canvasBackground.getBackground().setAlpha((solidOpacitySeekBar.getProgress()));
            }
        });

        solidOpacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvasBackground.getBackground().setAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        textColorSeekBar = findViewById(R.id.textColorSeekBar);
        textShadowSwitch = findViewById(R.id.textShadowSwitch);
        colorClose = findViewById(R.id.colorClose);
        colorCloseIcon = findViewById(R.id.colorCloseIcon);
        textShadowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setShadow(blur, leftRight, upDown, color);
                } else {
                    setShadow(0, leftRight, upDown, color);
                }
            }
        });

        colorClose.setOnClickListener(this);
        textColorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int color) {
                mainTextId.setText(mainTextId.getText().toString());
                mainSubText.setText(mainSubText.getText().toString());
                mainTextId.setTextColor(color);
                mainSubText.setTextColor(color);
                firstComma.setTextColor(color);
                secondComma.setTextColor(color);
            }
        });


    }

    public void setShadow(float mBlur, int mLeftRight, int mUpDown, int mColor) {
        shadowSwitch.setChecked(true);
        mainTextId.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        mainSubText.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);

        mainSubText.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        mainSubText.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        mainSubText.setShadowLayer(mBlur, mLeftRight, mUpDown, mColor);
        firstComma.setShadowLayer(mBlur, 0, 0, mColor);
        secondComma.setShadowLayer(mBlur, 0, 0, mColor);


    }


    //textView text multi color
    public void multiColorText() {
        String mainColorfulString, mainSubString;
        mainColorfulString = mainTextId.getText().toString();
        mainSubString = mainSubText.getText().toString();

        Spannable mainSpan, subSpin;
        mainSpan = new SpannableString(mainColorfulString);
        subSpin = new SpannableString(mainSubString);

        for (int i = 0, len = mainColorfulString.length(); i < len; i++) {
            mainSpan.setSpan(new ForegroundColorSpan(getRandomColor()), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (int i = 0, len = mainSubString.length(); i < len; i++) {
            mainSpan.setSpan(new ForegroundColorSpan(getRandomColor()), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            subSpin.setSpan(new ForegroundColorSpan(getRandomColor()), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        mainTextId.setText(mainSpan);
        mainSubText.setText(subSpin);
        firstComma.setTextColor(getRandomColor());
        secondComma.setTextColor(getRandomColor());
    }

    //background random gradient set
    private void setRandomGradient() {
        if (solidOpacitySeekBar.getProgress() < 100) {
            solidOpacitySeekBar.setProgress(100);
        }
        randomGradient = new GradientDrawable();

        randomColorList = new int[]{getRandomColor(), getRandomColor(), getRandomColor()};
        randomGradient.setColors(randomColorList);

        canvasBackground.setBackground(randomGradient);
        canvasBackground.getBackground().setAlpha(solidOpacitySeekBar.getProgress());


    }


    //random color set
    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    //background gradient set
    class GradientBackgroundClass extends BaseAdapter {
        @Override
        public int getCount() {
            return 99;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gradient_count_flow, parent, false);
            }
            final LinearLayout gradientFlow = convertView.findViewById(R.id.gradientFlow);
            final TextView gradientCount = convertView.findViewById(R.id.gradientCount);
            constanceGradient = new GradientDrawable();
            for (int i = 0; i < 99; i++) {
                if (position == i) {
                    if (position % 2 == 0) {
                        gradientColorList = new int[]{getRandomColor(), getRandomColor()};
                        constanceGradient.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    } else {
                        if (position % 3 == 0) {
                            constanceGradient.setOrientation(GradientDrawable.Orientation.TL_BR);
                        } else {
                            constanceGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                            //SWEEP_GRADIENT,
                        }
                        gradientColorList = new int[]{getRandomColor(), getRandomColor(), getRandomColor()};

                    }

                }
            }
            constanceGradient.setColors(gradientColorList);

            gradientFlow.setBackground(constanceGradient);

            gradientCount.setText(String.valueOf(1 + position));
            gradientFlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (solidOpacitySeekBar.getProgress() < 100) {
                        solidOpacitySeekBar.setProgress(100);
                    }
                    canvasBackground.setBackground(gradientFlow.getBackground());
                    canvasBackground.getBackground().setAlpha(solidOpacitySeekBar.getProgress());

                }
            });
            return convertView;
        }
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
            }
        });
    }

    private void setText() {
        closeKeyboard();
        String mainText = messageEdit.getText().toString();
        String whoSay = author.getText().toString();
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
        ImageView closeEditText, cleanEditText, submitEditText;
        ListView editTextListView;

        closeEditText = findViewById(R.id.closeEditText);
        cleanEditText = findViewById(R.id.cleanEditText);
        submitEditText = findViewById(R.id.submitEditText);
        messageEdit = findViewById(R.id.messageEditText);
        editTextListView = findViewById(R.id.editTextListView);
        author = findViewById(R.id.whoSayId);
        messageEdit.setText(mainTextId.getText().toString());
        if (!author.getText().toString().isEmpty()) {
            author.setText(mainSubText.getText().toString().substring(2, mainSubText.length()));
        }
        EditTextAdapter editTextAdapter = new EditTextAdapter(this, messageEdit, author);
        editTextListView.setAdapter(editTextAdapter);

        closeEditText.setOnClickListener(this);
        cleanEditText.setOnClickListener(this);
        submitEditText.setOnClickListener(this);
    }


    //<-------------------------------------------picture section------------------------------->

    public void openGallery(int requestImage) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 21) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, requestImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CANVAS_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                stopAnimation(backgroundList);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                canvasBackgroundImage.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
                fakeImageView.setImageBitmap(easyAccess.getResizedBitmap(bitmap));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Exception " + e, Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                waterImage1.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
                resultImage1.setVisibility(View.VISIBLE);
                resultImage1.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                waterImage2.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
                resultImage2.setVisibility(View.VISIBLE);
                resultImage2.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_REQUEST3 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                waterImage3.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
                resultImage3.setVisibility(View.VISIBLE);
                resultImage3.setImageBitmap(easyAccess.getResizedBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void openPictureList() {
        startAnimation(backgroundList);
        pictureListGridView = findViewById(R.id.backgroundGridView);
        backgroundBack = findViewById(R.id.backgroundBack);
        galleryOpen = findViewById(R.id.galleryOpenId);
        backgroundBack.setOnClickListener(this);
        galleryOpen.setOnClickListener(this);
        SelectBackgroundClass selectBackgroundClass = new SelectBackgroundClass(this, backgroundImage);
        pictureListGridView.setAdapter(selectBackgroundClass);
        pictureListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (propertyLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(propertyLayout);
        } else if (shadowLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(shadowLayout);
        } else if (watermarkLayout.getVisibility() == View.VISIBLE) {
            stopAnimation(watermarkLayout);
        }
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
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), fontArray[position]);
            TextView fontList = convertView.findViewById(R.id.fontList);
            fontList.setTypeface(typeface);
            return convertView;
        }
    }


    @Override
    public void onBackPressed() {

        if (!isOpen()) {
            removeView();
        } else {
            super.onBackPressed();
        }

    }
}

class SpacingClass {
    public float letterSpacing(float space) {
        if (space < -0.15) {
            space = -0.15f;
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