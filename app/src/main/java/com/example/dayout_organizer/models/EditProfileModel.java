package com.example.dayout_organizer.models;

import java.io.Serializable;

public class EditProfileModel implements Serializable {

    public boolean success;
    public String message;
    public Data data = new Data();

    public class Data{
        public int id;
        public int organizer_id;
        public String created_at;
        public String updated_at;
        public String bio;
        public User user = new User();
    }


    public class User{
        public int id;
        public String first_name;
        public String last_name;
        public String photo;
        public String gender;
        public String phone_number;
        public String confirm_at;
        public String created_at;
        public String updated_at;
        public String email;
    }

}
