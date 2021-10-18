package com.example.mareu;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Repository.MeetingRepository;
import com.example.mareu.Service.DummyMeetingsApiService;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mareu.Service.DummyMeetingsGenerator.PARTICIPANTS;
import static com.example.mareu.Service.DummyMeetingsGenerator.ROOM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ExampleUnitTest {
    private MeetingRepository mMeetingRepository;
    private List<Meeting> mMeetingList;
    private Calendar mCalendar = Calendar.getInstance();
    private Date currentTime = mCalendar.getTime();

    @Before
    public void setup() {
        mMeetingRepository = new MeetingRepository(new DummyMeetingsApiService());
        mMeetingList = mMeetingRepository.getMeetings();
    }


    @Test
    public void addMeetingWithSuccess() {
        Meeting mMeeting = new Meeting(currentTime, ROOM.get(1), "Réunion Test", PARTICIPANTS);
        mMeetingRepository.addMeeting(mMeeting);
        assertTrue(mMeetingList.contains(mMeeting));
    }


    @Test
    public void deleteMeetingWithSuccess() {
        Meeting mMeeting = new Meeting(currentTime, ROOM.get(1), "Réunion Test", PARTICIPANTS);
        mMeetingRepository.addMeeting(mMeeting);
        assertTrue(mMeetingList.contains(mMeeting));
        mMeetingRepository.deleteMeeting(mMeeting);
        assertFalse(mMeetingList.contains(mMeeting));
    }



    @Test
    public void filterByPlaceMeeting() {
        String placeFilter = "Salle 1";
        Meeting m1 = new Meeting(currentTime, ROOM.get(0), "Réunion Test", PARTICIPANTS);
        Meeting m2 = new Meeting(currentTime, ROOM.get(3), "Réunion Test", PARTICIPANTS);
        Meeting m3 = new Meeting(currentTime, ROOM.get(0), "Réunion Test", PARTICIPANTS);
        mMeetingRepository.addMeeting(m1);
        mMeetingRepository.addMeeting(m2);
        mMeetingRepository.addMeeting(m3);

        List<Meeting> meetingList = mMeetingRepository.filterByPlace(placeFilter);

        mMeetingRepository.filterByPlace(placeFilter);

        assertTrue(meetingList.size() == 2);
        for (Meeting m: meetingList) {
            assertEquals(m.getRoom().toString(),placeFilter);
        }
    }

    @Test
    public void filterByDateMeeting() {
        String dateFilter = "18/11/2021";
        mCalendar.set(2021, 10, 18);
        Date date = mCalendar.getTime();
        mCalendar.set(2021, 11, 18);
        Date date1 = mCalendar.getTime();

        Meeting m1 = new Meeting(date, ROOM.get(4), "Réunion Test", PARTICIPANTS);
        Meeting m2 = new Meeting(date1, ROOM.get(7), "Réunion Test", PARTICIPANTS);
        Meeting m3 = new Meeting(date, ROOM.get(9), "Réunion Test", PARTICIPANTS);

        mMeetingRepository.addMeeting(m1);
        mMeetingRepository.addMeeting(m2);
        mMeetingRepository.addMeeting(m3);

        List<Meeting> meetingList = mMeetingRepository.filterByDate(dateFilter);

        assertEquals(2, meetingList.size());
        for (Meeting m : meetingList) {
            assertEquals(m.getDateFormated(), dateFilter);
        }

    }
}