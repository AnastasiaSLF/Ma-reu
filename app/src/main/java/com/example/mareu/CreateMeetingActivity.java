package com.example.mareu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class CreateMeetingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);


        CreateMeetingsFragment fragment = new CreateMeetingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_create_meeting, fragment)
                .commit();
    }
}