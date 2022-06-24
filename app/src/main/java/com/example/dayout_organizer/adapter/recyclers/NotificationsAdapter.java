package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.ui.activities.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    List<NotificationData> notifications;
    Context context;

    public NotificationsAdapter(List<NotificationData> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    public void refresh(List<NotificationData> notifications){
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void insertRoomObject(NotificationData notificationData) {

        // insert object in room database
        ((MainActivity) context).iNotification
                .insertNotification(notificationData)
                .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        insertRoomObject(notifications.get(position));

        holder.notificationTitle.setText(notifications.get(position).title);
        holder.notificationDescription.setText(notifications.get(position).body);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.notification_title)
        TextView notificationTitle;

        @BindView(R.id.notification_description)
        TextView notificationDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
