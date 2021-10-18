package com.example.mareu;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.Dialogs.DialogDateFragment;
import com.example.mareu.MainActivity.MainActivity;
import com.example.mareu.Model.Meeting;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.RecyclerViewItemCount.withItemCount;
import static com.example.mareu.Service.DummyMeetingsGenerator.FAKE_MEETING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class FilterByDateInstruTest {

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
    public void filterByDateInstrumentedTest() {
        onView(ViewMatchers.withId(R.id.meeting_recycler_view)).check(withItemCount(ITEM_COUNT));

        onView(withId(R.id.menu_activity_item_sort_by))
                .perform(click());

        onView(withText("Filtre par date"))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DialogDateFragment.class.getName())));

        onView(withText("OK")).perform(click());

        onView(ViewMatchers.withId(R.id.meeting_recycler_view)).check(withItemCount(2));

    }
}
