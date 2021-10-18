package com.example.mareu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.Dialogs.DialogPlaceFragment;
import com.example.mareu.MainActivity.MainActivity;
import com.example.mareu.Model.Meeting;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.Service.DummyMeetingsGenerator.FAKE_MEETING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class FilterByPlaceInstruTest {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
        for (Meeting meeting: FAKE_MEETING) {
            mActivity.getMeetingRepository().addMeeting(meeting);
        }
    }


    @Test
    public void filterByPlaceInstrumentedTest() {
        onView(withId(R.id.menu_activity_item_sort_by))
                .perform(click());
        // Click on the item menu filter by date
        onView(withText("Filtre par lieu"))
                .perform(click());
        // Pick a date, example 13th march 2020 ( 1 meetings hardcoded in Meeting.initListe for this date )
        onView(withClassName(Matchers.equalTo(DialogPlaceFragment.class.getName())));
        onView(withText("OK")).perform(click());
        // We check that the count of items is 2 <-> Because 2 meetings hardcoded in DummyMaReuApiGenerator
        onView(withId(R.id.meeting_recycler_view)).check(RecyclerViewItemCount.withItemCount(1));

    }

}
