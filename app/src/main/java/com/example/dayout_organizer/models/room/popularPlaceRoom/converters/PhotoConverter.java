package com.example.dayout_organizer.models.room.popularPlaceRoom.converters;

import androidx.room.TypeConverter;


import com.example.dayout_organizer.models.popualrPlace.PopularPlacePhoto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PhotoConverter implements Serializable {

    @TypeConverter
    public String fromPopularPlacePhotoListToJson(List<PopularPlacePhoto> popularPlacePhotos) {

        if (popularPlacePhotos == null)
            return null;

        Type type = new TypeToken<List<PopularPlacePhoto>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(popularPlacePhotos, type);
    }


    @TypeConverter
    public List<PopularPlacePhoto> fromJsonToPhotoList(String photoObject) {


        if (photoObject == null)
            return null;

        Type type = new TypeToken<List<PopularPlacePhoto>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(photoObject, type);

    }


}



