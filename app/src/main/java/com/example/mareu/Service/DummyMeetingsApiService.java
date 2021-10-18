package com.example.mareu.Service;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class DummyMeetingsApiService implements MeetingsApiService {

    private List<Room> mRoomList = DummyMeetingsGenerator.generateRooms();
    private List<Meeting> mMeetings = DummyMeetingsGenerator.generateMeetings();



    @Override
    public List<Meeting> getMeetings() {
        return this.mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);

    }


    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }


    @Override
    public List<Room> getRooms() {
        return this.mRoomList;
    }

    @Override
    public List<Meeting> filterByDate(String date) {
        List<Meeting> meetingListByDate = new ArrayList<>();
        for (Meeting meeting: mMeetings) {
            if (meeting.getDateFormated().equals(date)){
                meetingListByDate.add(meeting);
            }
        }
        return meetingListByDate;
    }

    @Override
    public List<Meeting> filterByPlace(String place) {
        List<Meeting> meetingListByPlace = new ArrayList<>();
        for (Meeting meeting: mMeetings) {
            if (meeting.getRoom().toString() == place){
                meetingListByPlace.add(meeting);
            }
        }
        return meetingListByPlace;
    }


}





