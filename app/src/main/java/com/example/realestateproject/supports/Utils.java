package com.example.realestateproject.supports;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Utils {
    public static Bitmap decodeBase64Image(String base64Img) {
        String base64String;
        if(base64Img.indexOf("data:image/png;base64,") > -1){
            base64String = base64Img.replace("data:image/png;base64,","");
        } else base64String = base64Img;
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
