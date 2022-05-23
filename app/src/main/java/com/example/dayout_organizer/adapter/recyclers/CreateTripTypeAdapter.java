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
import com.example.dayout_organizer.models.trip.TripType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTripTypeAdapter extends RecyclerView.Adapter<CreateTripTypeAdapter.ViewHolder> {

    List<TripType> list;
    Context context;
    OnItemClick onItemClick;

    public CreateTripTypeAdapter(List<TripType> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refresh(List<TripType> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_trip_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.type_name)
        TextView typeName;
        @BindView(R.id.cancel_btn)
        ImageButton cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cancelButton.setOnClickListener(v -> {
                list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClick.OnCreateTripTypeItemClicked(getAdapterPosition(),list);
            });
        }
    }

    public interface OnItemClick {
        void OnCreateTripTypeItemClicked(int position, List<TripType> list);
    }
}
