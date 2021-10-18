package com.example.mareu;

import com.example.mareu.Model.Meeting;

public class DeleteMeetingEvent {
    public Meeting mMeeting;

    public DeleteMeetingEvent(Meeting meeting){
        this.mMeeting = meeting;
    }
}