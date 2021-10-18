package com.example.mareu;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.Repository.MeetingRepository;

public abstract class BaseActivity extends AppCompatActivity {

    public MeetingRepository getMeetingRepository(){
        return ((MareuApp) getApplication()).getMeetingRepository();
    }
}
