package com.example.mareu.Repository;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.Room;
import com.example.mareu.Service.DummyMeetingsApiService;

import java.util.List;

public class MeetingRepository {

    private final DummyMeetingsApiService mMeetingApiService;

    public MeetingRepository(DummyMeetingsApiService meetingApiService) { mMeetingApiService = meetingApiService; }

    public List<Meeting> getMeetings(){
        List<Meeting> meetings = mMeetingApiService.getMeetings();
        return meetings;
    }

    public void addMeeting(Meeting meeting){
        mMeetingApiService.addMeeting(meeting);
    }

    public void deleteMeeting(Meeting meeting){
        mMeetingApiService.deleteMeeting(meeting);
    }

    public List<Room> getMeetingsRoomsList(){
        List<Room> roomList = mMeetingApiService.getRooms();
        return roomList;
    }

    public List<Meeting> filterByDate(String date){ return mMeetingApiService.filterByDate(date);}

    public List<Meeting> filterByPlace(String place){ return mMeetingApiService.filterByPlace(place);}


}