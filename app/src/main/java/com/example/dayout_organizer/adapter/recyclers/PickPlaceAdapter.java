package com.example.dayout_organizer.adapter.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.place.PlaceData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickPlaceAdapter extends RecyclerView.Adapter<PickPlaceAdapter.ViewHolder> {
    List<PlaceData> list;
    Context context;
    OnItemClick onItemClick;


    public PickPlaceAdapter(List<PlaceData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void refresh(List<PlaceData> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.placeName.setText(list.get(position).name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.place_name)
        TextView placeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.OnCreateTripPlaceItemClicked(getAdapterPosition(), list);
        }
    }

    public interface OnItemClick {
        void OnCreateTripPlaceItemClicked(int position, List<PlaceData> list);
    }
}
