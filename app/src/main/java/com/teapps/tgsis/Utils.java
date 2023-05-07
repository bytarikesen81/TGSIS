package com.teapps.tgsis;

import android.app.Activity;
import android.widget.Toast;

public class Utils {
    public static void toastLongMessage(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
    public static void toastShortMessage(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
