package com.example.dayout_organizer.adapter.recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;

import com.example.dayout_organizer.models.place.PopularPlaceData;
import com.example.dayout_organizer.models.place.PlacePhoto;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.home.PlaceInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class HomePlaceAdapter extends RecyclerView.Adapter<HomePlaceAdapter.ViewHolder> {

    private static final String TAG = "Home Place Adapter";
    List<PopularPlaceData> list;
    Context context;


    public HomePlaceAdapter(List<PopularPlaceData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void refreshList(List<PopularPlaceData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void insertRoomObject(PopularPlaceData popularPlace) {

        // insert object in room database
        ((MainActivity) context).roomPopularPlaces
                .insertPopularPlace(popularPlace)
                .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        insertRoomObject(list.get(position));

        holder.placeName.setText(list.get(position).name);
        holder.shortDescrption.setText(list.get(position).summary);
        holder.bindImageSlider(list.get(position).photos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.place_name)
        TextView placeName;
        @BindView(R.id.short_descrption)
        TextView shortDescrption;
        @BindView(R.id.image_slider)
        ImageSlider imageSlider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            //FIXME: Check if Drawer is not opened - Eyad.
            FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity)context, new PlaceInfoFragment(list.get(getAdapterPosition())));
        }

        private void bindImageSlider(List<PlacePhoto> photos) {
//            List<SlideModel> slideModels = new ArrayList<>();
//
//            for (PlacePhoto ph : photos) {
//                slideModels.add(new SlideModel(PLACE_PHOTO_URL + ph.id
//                        , ScaleTypes.FIT));
//            }
//
//            imageSlider.setImageList(slideModels);
//
//            imageSlider.setScrollBarFadeDuration(10000);
        }


    }
}
