package app.gunjan.twilio;

import android.util.Log;

public class Logger {


    public static void show(String tag, String mess) {
        Log.e(tag, mess);
    }

    public static void e(String text){
        Log.e("MAX",""+text);
    }

}
