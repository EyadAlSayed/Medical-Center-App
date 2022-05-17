package com.example.dayout_organizer.adapter.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTripPlaceAdapter extends RecyclerView.Adapter<CreateTripPlaceAdapter.ViewHolder> {

    List<CreateTripPlace.Place> list;
    Context context;
    OnItemClick onItemClick;

    public CreateTripPlaceAdapter(List<CreateTripPlace.Place> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refresh(List<CreateTripPlace.Place> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_trip_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.placeName.setText(list.get(position).place_name);
        holder.shortDescription.setText(list.get(position).description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place_name)
        TextView placeName;
        @BindView(R.id.short_description)
        TextView shortDescription;
        @BindView(R.id.cancel_btn)
        ImageButton cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cancelButton.setOnClickListener(v -> {
                list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClick.OnCreateTripPlaceItemClicked(getAdapterPosition(),list);
            });
        }


    }
    public interface OnItemClick {
        void OnCreateTripPlaceItemClicked(int position, List<CreateTripPlace.Place> list);
    }
}
