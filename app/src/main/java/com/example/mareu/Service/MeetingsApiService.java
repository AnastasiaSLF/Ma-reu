package com.example.mareu.Service;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.Room;

import java.util.Calendar;
import java.util.List;

public interface MeetingsApiService {



    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    List<Room> getRooms();

    List<Meeting> filterByDate(String date);

    List<Meeting> filterByPlace(String place);
}
