package com.example.macker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.util.Random;


public class ImageFilter {
    public static final double PI = 3.14159d;
    public static final double FULL_CIRCLE_DEGREE = 360d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;
    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    private float defaultBitmapScale = 0.1f;

    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    public Bitmap applyShadingFilter(Bitmap source, int shadingColor) {
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);

        int index = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                index = y * width + x;
                // AND
                pixels[index] &= shadingColor;
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    } //Color.RED,Color.GREEN etc

    public Bitmap applyTintEffect(Bitmap src, int degree) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pix = new int[width * height];
        src.getPixels(pix, 0, width, 0, 0, width, height);
        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
        double angle = (PI * (double) degree) / HALF_CIRCLE_DEGREE;
        int S = (int) (RANGE * Math.sin(angle));
        int C = (int) (RANGE * Math.cos(angle));
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                RY = (70 * r - 59 * g - 11 * b) / 100;
                GY = (-30 * r + 41 * g - 11 * b) / 100;
                BY = (-30 * r - 59 * g + 89 * b) / 100;
                Y = (30 * r + 59 * g + 11 * b) / 100;
                RYY = (S * BY + C * RY) / 256;
                BYY = (C * BY - S * RY) / 256;
                GYY = (-51 * RYY - 19 * BYY) / 100;
                R = Y + RYY;
                R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
                G = Y + GYY;
                G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
                B = Y + BYY;
                B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
                pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }
        // output bitmap
        Bitmap outBitmap = Bitmap.createBitmap(width, height, src.getConfig());
        outBitmap.setPixels(pix, 0, width, 0, 0, width, height);
        pix = null;
        return outBitmap;
    } // seekBar change

    // extra
    public Bitmap flipImage(Bitmap src, int type) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        if(type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        }
        else if(type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return null;
        }

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }
    public Bitmap blurRenderScript(Context context , Bitmap smallBitmap, int radius) {
        if (radius == 0) {
            return smallBitmap;
        }
        int width  = Math.round(smallBitmap.getWidth() * defaultBitmapScale);
        int height = Math.round(smallBitmap.getHeight() * defaultBitmapScale);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(smallBitmap, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(radius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
    public Bitmap getRefelection(Bitmap image) {
        final int reflectionGap = 0;
        Bitmap originalImage = image;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x99ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        if (originalImage != null && originalImage.isRecycled()) {
            originalImage.recycle();
            originalImage = null;
        }
        if (reflectionImage != null && reflectionImage.isRecycled()) {
            reflectionImage.recycle();
            reflectionImage = null;
        }
        return bitmapWithReflection;
    }

    public Bitmap tintImage(Bitmap originalImage, int degree) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int[] pix = new int[width * height];
        originalImage.getPixels(pix, 0, width, 0, 0, width, height);
        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
        double angle = (PI * (double) degree)
                / HALF_CIRCLE_DEGREE;
        int S = (int) (RANGE * Math.sin(angle));
        int C = (int) (RANGE * Math.cos(angle));
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                RY = (70 * r - 59 * g - 11 * b) / 100;
                GY = (-30 * r + 41 * g - 11 * b) / 100;
                BY = (-30 * r - 59 * g + 89 * b) / 100;
                Y = (30 * r + 59 * g + 11 * b) / 100;
                RYY = (S * BY + C * RY) / 256;
                BYY = (C * BY - S * RY) / 256;
                GYY = (-51 * RYY - 19 * BYY) / 100;
                R = Y + RYY;
                R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
                G = Y + GYY;
                G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
                B = Y + BYY;
                B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
                pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }

        Bitmap outBitmap = Bitmap.createBitmap(width, height, originalImage.getConfig());
        outBitmap.setPixels(pix, 0, width, 0, 0, width, height);
        pix = null;
        return outBitmap;
    }

    public Bitmap applySnowEffect(Bitmap originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int[] pixels = new int[width * height];
        originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 50;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                index = y * width + x;
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                thresHold = random.nextInt(COLOR_MAX);
                if (R > thresHold && G > thresHold && B > thresHold) {
                    pixels[index] =
                            Color.rgb(COLOR_MAX, COLOR_MAX,
                                    COLOR_MAX);
                }
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }

    public Bitmap applyBlackFilter(Bitmap originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int[] pixels = new int[width * height];
        originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int R, G, B, index = 0, thresHold = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                index = y * width + x;
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                thresHold = random.nextInt(COLOR_MAX);
                if (R < thresHold && G < thresHold && B < thresHold) {
                    pixels[index] =
                            Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
                }
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }

    public Bitmap applyFleaEffect(Bitmap originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int[] pixels = new int[width * height];
        originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int index = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                index = y * width + x;
                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX));
                pixels[index] |= randColor;
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }

    public Bitmap roundCorner(Bitmap originalImage, double round) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        float newRound = (float) round;
        canvas.drawRoundRect(rectF, newRound, newRound, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originalImage, rect, rect, paint);
        return result;
    }

    public Bitmap rotate(Bitmap originalImage, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(originalImage, 0, 0, originalImage.getWidth(),
                originalImage.getHeight(), matrix, true);
    }

    public Bitmap doGreyScale(Bitmap originalImage) {
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;
        Bitmap resultingBitmap =
                Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(),
                        originalImage.getConfig());
        int A, R, G, B;
        int pixel;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = originalImage.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                resultingBitmap.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return resultingBitmap;
    }

    public Bitmap doInvert(Bitmap originalImage) {
        Bitmap resultingBitmap =
                Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(),
                        originalImage.getConfig());
        int A, R, G, B;
        int pixelColor;
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelColor = originalImage.getPixel(x, y);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                resultingBitmap.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return resultingBitmap;
    }

    public Bitmap boost(Bitmap originalImage, int type, double percent) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = originalImage.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                switch (type) {
                    case RED:
                        R = (int) (R * (1 + percent));
                        if (R > 255) R = 255;
                        break;
                    case GREEN:
                        G = (int) (G * (1 + percent));
                        if (G > 255) G = 255;
                        break;
                    case BLUE:
                        B = (int) (B * (1 + percent));
                        if (B > 255) B = 255;
                        break;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
}
