package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Poll_Pagination_Data_Table")
public class PollPaginationData implements Serializable {
    int current_page;
    @TypeConverters
    public List<PollData> data = new ArrayList<>();
    public String next_page_url;
    public String prev_page_url;
    public int total;
}
