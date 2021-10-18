package com.example.mareu.Service;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract  class DummyMeetingsGenerator {
    public static List<Meeting> MEETINGS = Arrays.asList();


    static List<Room> generateRooms() {
        return new ArrayList<>(ROOM);
    }

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS);
    }
    public static List<String> PARTICIPANTS = Arrays.asList(
            "Samuel@lamzone.com",
            "Marie@lamzone.com"
    );

    public static List<Room> ROOM = Arrays.asList(
            new Room("Salle 1", "#FF9800"),
            new Room("Salle 2", "#9ABCA4"),
            new Room("Salle 3", "#E9D0C6"),
            new Room("Salle 4", "#009688"),
            new Room("Salle 5", "#FFFFEB3B"),
            new Room("Salle 6", "#FF9C27B0"),
            new Room("Salle 7", "#E9D0C6"),
            new Room("Salle 8", "#FFF44336"),
            new Room("Salle 9", "FF2196F3"),
            new Room("Salle 10", "#FFBB86FC")
    );

    public static Calendar mCalendar = Calendar.getInstance();
    public static Date mDate = mCalendar.getTime();



    public static List<Meeting> FAKE_MEETING = Arrays.asList(
            new Meeting(mDate,ROOM.get(0),"Réunion A",PARTICIPANTS),
            new Meeting(mDate,ROOM.get(1), "Réunion B", PARTICIPANTS)
    );


}

