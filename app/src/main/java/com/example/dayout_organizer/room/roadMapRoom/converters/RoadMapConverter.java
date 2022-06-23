package com.example.dayout_organizer.room.roadMapRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.profile.ProfileData;
import com.example.dayout_organizer.models.roadMap.RoadMapData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class RoadMapConverter implements Serializable {

    @TypeConverter
    public String fromRoadMap(RoadMapData roadMapData) {

        if (roadMapData == null)
            return null;

        Type type = new TypeToken<RoadMapData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(roadMapData, type);
    }


    @TypeConverter
    public RoadMapData toRoadMap(String data) {


        if (data == null)
            return null;

        Type type = new TypeToken<List<RoadMapData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(data, type);

    }
}
