package com.example.dayout_organizer.models.room.profileRoom.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dayout_organizer.models.profile.ProfileData;
import com.example.dayout_organizer.models.profile.ProfileUser;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_DATA;

@Dao
public interface IProfileModel {

    @Insert
    Completable insertProfile(ProfileData profileData);

    @Query("select * from " + PROFILE_DATA+" where id = :userId")
    Single<ProfileData> getProfile(int userId);
}
