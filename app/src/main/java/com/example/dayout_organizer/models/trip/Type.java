package com.example.dayout_organizer.models.trip;

import java.io.Serializable;
import java.util.List;

public class Type implements Serializable {

    public class Data {
        public int id;
        public String name;
    }

    public boolean success;
    public String message;
    public List<Data> data;
}
