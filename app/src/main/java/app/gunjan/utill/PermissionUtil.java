package app.gunjan.utill;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;


public class PermissionUtil {
    private static String[] galleryPermissions = {
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    public static String[] contactPermissions = {
            "android.permission.READ_CONTACTS"
    };


    private static String[] cameraPermissions = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    public static String[] getGalleryPermissions(){
        return galleryPermissions;
    }

    public static String[] getCameraPermissions() {
        return cameraPermissions;
    }

    public static boolean verifyPermissions(Context context, String[] grantResults) {
        for (String result : grantResults) {
            if (ActivityCompat.checkSelfPermission(context, result) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean checkMarshMellowPermission(){
        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static void requestPermission(String[] grantResults, Activity activity){
        ActivityCompat.requestPermissions(activity, grantResults,1000);
    }

}
