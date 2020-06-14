package com.example.iskandar.view.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AssetManager {

    public static String loadJson(Context context) {
        String jsonResult = "";
        try {
            InputStream stream = context.getAssets().open("players.json");
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            jsonResult = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static Drawable drawableFromAsset(Context context, String filename) {
        Drawable drawable = null;
        try {
            InputStream inputStream = context.getAssets().open(filename);
            drawable = Drawable.createFromStream(inputStream, filename);
            inputStream.close();

        } catch (IOException e) {
            //handle exception
            e.printStackTrace();
        }
        return drawable;
    }

    public static Typeface loadTypeface(Context context, String fontName){
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
    }
}
