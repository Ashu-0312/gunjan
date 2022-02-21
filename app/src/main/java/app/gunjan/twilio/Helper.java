package app.gunjan.twilio;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Helper {

    public static Uri fileUri;
    public static Uri getOutputMediaFileUri(Context context) {

        return FileProvider.getUriForFile(context, "com.sell.datingapp.provider", Objects.requireNonNull(getOutputMediaFile(context)));
    }

    private static File getOutputMediaFile(Context context) {
        // External sdcard location
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            /*ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            return   resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);*/

            File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "SWAGTING-APP");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            // Create a media file name

            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        } else {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SWAGTING-APP");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            // Create a media file name

            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

            return mediaFile;
        }
    }

    public static String getRealPathForImagesURI(Uri contentUri, Activity context) {
        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        if (!TextUtils.isEmpty(cursor.getString(column_index))) {
            return cursor.getString(column_index);
        } else {
            return getImagePathFromInputStreamUri(context, contentUri);
        }

    }

    public static String getImagePathFromInputStreamUri(Activity context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;
        String fileName = "";

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed

                String scheme = uri.getScheme();
                if (scheme.equals("file")) {
                    fileName = uri.getLastPathSegment();
                } else if (scheme.equals("content")) {
                    String splitableuri = String.valueOf(uri);
                    String[] fileSplit = splitableuri.split("/");

                    fileName = fileSplit[fileSplit.length - 1];
                    fileName = fileName + ".jpg";

                }

                File testFile = new File(context.getExternalCacheDir(), fileName);
                if (testFile.exists()) {
                    testFile.delete();

                }
                File photoFile = createTemporalFileFrom(context, inputStream, fileName);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    public static File createTemporalFileFrom(Activity context, InputStream inputStream, String imageFileName) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[50 * 1024];
            targetFile = createTemporalFile(context, imageFileName);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createTemporalFile(Context context, String filename) {

        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);
    }
}
