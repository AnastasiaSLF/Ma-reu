package com.example.mareu.DI;

import com.example.mareu.Repository.MeetingRepository;
import com.example.mareu.Service.DummyMeetingsApiService;

public class DI {

    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyMeetingsApiService());
    }
}