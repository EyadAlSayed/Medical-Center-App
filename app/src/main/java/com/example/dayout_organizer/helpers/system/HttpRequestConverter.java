package com.example.dayout_organizer.helpers.system;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpRequestConverter {

    public static RequestBody createFileAsRequestBody(String mediaType, File body) {
        return RequestBody.create(MediaType.parse(mediaType), body);
    }

    public static RequestBody createByteArrayAsRequestBody(String mediaType, byte[] body) {
        return RequestBody.create(MediaType.parse(mediaType), body);
    }


    public static RequestBody createStringAsRequestBody(String mediaType, String body) {
        return RequestBody.create(MediaType.parse(mediaType), body);
    }

    public static MultipartBody.Part createFormDataAttribute(String keyName,String body){
     return  MultipartBody.Part.createFormData(keyName,body);
    }

    public static MultipartBody.Part createFormDataFile(String keyName, String fileName, RequestBody body) {
        return MultipartBody.Part.createFormData(keyName, fileName,
                body);
    }
}
