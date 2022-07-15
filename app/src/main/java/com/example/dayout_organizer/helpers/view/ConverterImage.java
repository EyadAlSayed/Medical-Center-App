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

import com.example.dayout_organizer.helpers.system.DateConverter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
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

    public static byte[] getBytesFromUri(InputStream inputStream,int bufferCapacity) {

        try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferCapacity];

            int len;
            while (true) {
                len = inputStream.read(buffer);
                if(len < 0) break;
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void writeByteAsFile(Activity activity,Uri uri,String filePath,int bufferCapacity) {

        try {
            InputStream in =  activity.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[bufferCapacity];
            int len;
            while(true){
                len=in.read(buf);
                if(len < 0) break;
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }catch (Exception e){
            Log.e("writeByteAsFile", "writeByteAsFile: ",e );
        }


    }

    public static String createImageFilePath(Activity activity,Uri uri) {
        try {
            // Create an image file name
            String timeStamp = DateConverter.getTimeStampAs("yyyyMMdd_HH_mm_ss");
            String imageFileName = "dayOut" + timeStamp + "_";

            File storageDir = activity.getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            String path = image.getAbsolutePath();
            writeByteAsFile(activity,uri,path,2048);
            return path;
        } catch (IOException e) {
            Log.e("createImageFilePath", "createImageFilePath: ",e );
        }
        return "";
    }




}
