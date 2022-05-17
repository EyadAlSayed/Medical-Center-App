package com.example.dayout_organizer.models.room.popularPlaceRoom.converters;

import androidx.room.TypeConverter;


import com.example.dayout_organizer.models.place.PlacePhoto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PhotoConverter implements Serializable {

    @TypeConverter
    public String fromPopularPlacePhotoListToJson(List<PlacePhoto> placePhotos) {

        if (placePhotos == null)
            return null;

        Type type = new TypeToken<List<PlacePhoto>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(placePhotos, type);
    }


    @TypeConverter
    public List<PlacePhoto> fromJsonToPhotoList(String photoObject) {


        if (photoObject == null)
            return null;

        Type type = new TypeToken<List<PlacePhoto>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(photoObject, type);

    }


}



