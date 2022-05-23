package com.example.dayout_organizer.models;

import java.util.ArrayList;

public class NotificationModel {

    public ArrayList<Data> data = new ArrayList<>();

    public class Data{
        public int id;
        public String title;
        public String body;
        public int user_id;
        public String created_at;
        public String updated_at;
    }
}
