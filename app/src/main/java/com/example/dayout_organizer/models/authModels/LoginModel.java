package com.example.dayout_organizer.models.authModels;

import java.io.Serializable;
import java.util.List;

public class LoginModel implements Serializable {

    public class Pivot{
        public int user_id;
        public int role_id;
    }

    public class Role{
        public int id;
        public String name;
        public String created_at;
        public String updated_at;
        public Pivot pivot;
    }

    public class Data{
        public int id;
        public List<Role> role;
        public String token;
    }

    public boolean success;
    public String message;
    public Data data;

}
