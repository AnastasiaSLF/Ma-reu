package com.example.mareu;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.MainActivity.MainActivity;
import com.example.mareu.Model.Meeting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static com.example.mareu.RecyclerViewItemCount.withItemCount;
import static com.example.mareu.Service.DummyMeetingsGenerator.FAKE_MEETING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class DeleteMeetingInstrumentedTest {

    private MainActivity mActivity;
    private int ITEM_COUNT;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        for (Meeting meeting: FAKE_MEETING) {
            mActivity.getMeetingRepository().addMeeting(meeting);
        }
        List<Meeting> meetingList = mActivity.getMeetingRepository().getMeetings();
        ITEM_COUNT = meetingList.size();
    }


    @Test
    public void MeetingList_shouldNotBeEmpty(){
        onView(ViewMatchers.withId(R.id.meeting_recycler_view))
                .check(withItemCount(ITEM_COUNT));
    }


    @Test
    public void meetingList_deleteAction_shouldRemoveItem() {
        onView(ViewMatchers.withId(R.id.meeting_recycler_view)).check(withItemCount(ITEM_COUNT));

        onView(ViewMatchers.withId(R.id.meeting_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteAction()));

        onView(ViewMatchers.withId(R.id.meeting_recycler_view)).check(withItemCount(ITEM_COUNT -1));
    }
}
