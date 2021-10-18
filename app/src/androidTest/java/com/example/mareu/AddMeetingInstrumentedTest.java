package com.example.mareu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.Dialogs.DialogDateFragment;
import com.example.mareu.Dialogs.DialogTimeFragment;
import com.example.mareu.MainActivity.MainActivity;
import com.example.mareu.Repository.MeetingRepository;
import com.example.mareu.Service.DummyMeetingsApiService;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.RecyclerViewItemCount.withItemCount;


@RunWith(AndroidJUnit4.class)
public class AddMeetingInstrumentedTest {
    public MeetingRepository mMeetingRepository;
    public int ITEM_COUNT;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        mMeetingRepository = new MeetingRepository(new DummyMeetingsApiService());
        ITEM_COUNT = mMeetingRepository.getMeetings().size();
    }


    @Test
    public void addMeetingWithSuccess() {

        onView(withId(R.id.add_meeting_fab))
                .perform(click());
        onView(withId(R.id.edit_subject))
                .perform(click());
        onView(withId(R.id.edit_subject))
                .perform(typeText("New meeting"));
        onView(withId(R.id.btn_date))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DialogDateFragment.class.getName())));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.btn_heure))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DialogTimeFragment.class.getName())));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.btn_participants))
                .perform(click());
        onView(withId(R.id.contributor_edit_txt))
                .perform(typeText("Sam@lamzone.com"));
        onView(withText("Valider")).perform(click());

        onView(withId(R.id.create_meeting_button))
                .perform(click());

        onView(withId(R.id.meeting_recycler_view)).check((withItemCount(ITEM_COUNT + 1)));
    }

}
