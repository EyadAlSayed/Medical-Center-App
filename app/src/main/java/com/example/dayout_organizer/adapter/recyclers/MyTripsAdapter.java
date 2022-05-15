package com.example.dayout_organizer.adapter.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.TripModel;

import java.util.List;

public class MyTripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<TripModel> list;
    Context context;

    public MyTripsAdapter(List<TripModel> list, Context context){
        this.context = context;
        this.list = list;
    }

    public void refreshList(List<TripModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType){
//
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).state == "Old")
            return 1;
        return 1;
    }

    class ViewHolderOld extends RecyclerView.ViewHolder{

        public ViewHolderOld(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ViewHolderUpcoming extends RecyclerView.ViewHolder{

        public ViewHolderUpcoming(@NonNull View itemView) {
            super(itemView);
        }
    }
}
