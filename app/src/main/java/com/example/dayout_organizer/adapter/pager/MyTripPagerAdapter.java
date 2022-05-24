package com.example.dayout_organizer.adapter.pager;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTripPagerAdapter extends FragmentStateAdapter {


    private final List<Pair<Fragment,String>> list;
    public MyTripPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Pair<Fragment,String>> list) {
        super(fragmentActivity);
        this.list = list;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position).first;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
