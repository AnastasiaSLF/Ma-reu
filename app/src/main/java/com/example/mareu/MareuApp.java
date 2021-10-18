package com.example.mareu;

import android.app.Application;
import android.content.res.Configuration;

import com.example.mareu.DI.DI;
import com.example.mareu.Repository.MeetingRepository;

public class  MareuApp extends Application {

    private MeetingRepository mMeetingRepository;

    public MeetingRepository getMeetingRepository() {
        if (mMeetingRepository == null){
            mMeetingRepository = DI.createMeetingRepository();
        }
        return mMeetingRepository;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mMeetingRepository = null;
    }
}