package com.example.mareu.MainActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.mareu.BaseActivity;
import com.example.mareu.CreateMeetingActivity;
import com.example.mareu.MeetingFragment;
import com.example.mareu.MeetingRecyclerViewAdapter;
import com.example.mareu.R;
import com.example.mareu.Service.MeetingsApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
  public static MeetingsApiService mMeetingsApiService;
    public FloatingActionButton add_meeting_fab;

    @BindView(R.id.meeting_recycler_view) RecyclerView mRecyclerView;
    private MeetingRecyclerViewAdapter mMeetingRecyclerViewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureFragment();
        configureToolbar();
        configureFABAddMeeting();
    }


    private void configureFragment() {
        MeetingFragment fragment = new MeetingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_main, fragment)
                .commit();
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureFABAddMeeting() {
        add_meeting_fab = findViewById(R.id.add_meeting_fab);
        add_meeting_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateMeetingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }



}
