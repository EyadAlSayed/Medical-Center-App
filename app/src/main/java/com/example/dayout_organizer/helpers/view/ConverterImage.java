package com.example.dayout_organizer.helpers.view;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConverterImage {

    /**
     * convert any image type Base64 to Bitmap
     *
     * @param base64_Image string type Base64
     * @return Bitmap for image
     */
    public static Bitmap convertBase64ToBitmap(String base64_Image) {
        byte[] decodedString = Base64.decode(base64_Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    /**
     * convert any Bitmap to image type Base64
     *
     * @param bitmap image bitmap
     * @return string Base64
     */
    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    /**
     * convert any image with type URI to Base64
     *
     * @param context      needed to convert URI to Bitmap
     * @param selectedFile an image with type URI
     * @return string type Base64
     */
    public static String convertUriToBase64(Context context, Uri selectedFile) {
        Bitmap bitmap;
        String encodedString;


        if (selectedFile != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedFile);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //  bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            bitmap = getResizedBitmap(bitmap, 300);

            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

            byte[] byteArray = outputStream.toByteArray();

            encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return "";
        }
        return encodedString;
    }


    public Bitmap compress(Bitmap yourBitmap) {
        //converted into webp into lowest quality
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourBitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);//0=lowest, 100=highest quality
        byte[] byteArray = stream.toByteArray();

        //convert your byteArray into bitmap
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static String resizeBase64Image(String base64image) {
        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 200, 200, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("SAED_", "getRealPathFromURI: ", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    /**
     * reduces the size of the image
     *
     * @param image
     * @param maxSize
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static byte[] getBytesFromUri(InputStream inputStream) {

        try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) == -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeByteAsFile(byte[] bytes,String filePath) {

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createImageFilePath(byte[] bytes,Context context) {
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.ENGLISH).format(new Date());
            String imageFileName = "dayOut" + timeStamp + "_";

            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            String path = image.getAbsolutePath();
            writeByteAsFile(bytes,path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
