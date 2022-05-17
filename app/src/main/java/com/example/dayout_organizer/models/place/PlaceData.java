package com.example.dayout_organizer.models.place;

import java.io.Serializable;
import java.util.List;

public class PlaceData implements Serializable {

    public int id;
    public String name;
    public String address;
    public String summary;
    public String description;
    public int type_id;
    public List<PlacePhoto> photos;
}
