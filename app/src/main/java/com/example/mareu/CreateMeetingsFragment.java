package com.example.mareu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mareu.Dialogs.DialogDateFragment;
import com.example.mareu.Dialogs.DialogParticipantsFragment;
import com.example.mareu.Dialogs.DialogTimeFragment;
import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.Room;
import com.example.mareu.Repository.MeetingRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mareu.Utils.isEmailValid;


public class CreateMeetingsFragment extends Fragment implements View.OnClickListener, DialogTimeFragment.DialogTimePickerListener, DialogParticipantsFragment.DialogParticipantsListener, DialogDateFragment.DialogDatePickerListener {


    @BindView(R.id.edit_subject)
    TextView mMeetingSubject;
    @BindView(R.id.btn_heure)
    Button mMeetingSelectTime;
    @BindView(R.id.room_spinner)
    Spinner mMeetingRoom;
    @BindView(R.id.btn_participants)
    Button mContributorSelector;
    @BindView(R.id.create_meeting_button)
    Button mCreateMeetingBtn;
    @BindView(R.id.back_btn)
    ImageButton mBackBtn;
    @BindView(R.id.list_contributor_tv)
    TextView mContributorList;
    @BindView(R.id.btn_date)
    Button mDateSelectorBtn;

    public MeetingRepository mMeetingRepository;
    private String subjet;
    private String timeFormated;
    private String dateFormated;
    private Room selectedRoom;
    private List<String> participants = new ArrayList<String>();

    private Calendar calendar = Calendar.getInstance();
    private Date mDate;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHours;
    private int mMinutes;

    private final int TAG_BUTTON_CONTRIBUTOR = 0;
    private final int TAG_BUTTON_TIME = 1;
    private final int TAG_BUTTON_CANCEL = 2;
    private final int TAG_BUTTON_CREATE = 3;
    private final int TAG_BUTTON_DATE = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_meetings, container, false);
        ButterKnife.bind(this, view);
        mMeetingRepository = ((BaseActivity) getActivity()).getMeetingRepository();
        configureClickListener();
        configureRoomSpinner();
        return view;
    }

    private void configureClickListener() {
        mContributorSelector.setOnClickListener(this);
        mContributorSelector.setTag(TAG_BUTTON_CONTRIBUTOR);
        mMeetingSelectTime.setOnClickListener(this);
        mMeetingSelectTime.setTag(TAG_BUTTON_TIME);
        mBackBtn.setOnClickListener(this);
        mBackBtn.setTag(TAG_BUTTON_CANCEL);
        mCreateMeetingBtn.setOnClickListener(this);
        mCreateMeetingBtn.setTag(TAG_BUTTON_CREATE);
        mDateSelectorBtn.setOnClickListener(this);
        mDateSelectorBtn.setTag(TAG_BUTTON_DATE);
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case TAG_BUTTON_CANCEL:
                getActivity().finish();
                break;
            case TAG_BUTTON_TIME:
                showTimePikerDialog();
                break;
            case TAG_BUTTON_CONTRIBUTOR:
                showContributorDialog();
                break;
            case TAG_BUTTON_CREATE:
                createMeeting();
                break;
            case TAG_BUTTON_DATE:
                showDatePikerDialog();
                break;
            default:
        }
    }

    private void showDatePikerDialog() {
        DialogDateFragment dialog = new DialogDateFragment();
       dialog.addListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "DialogDateFragment");
    }

    private void createMeeting() {
        subjet = mMeetingSubject.getText().toString();
        if (subjet.isEmpty()) {
            mMeetingSubject.setError(getText(R.string.infoAddSubjectToMeeting));
        } else if ((timeFormated == null) || (dateFormated == null)) {
            Toast.makeText(getContext(), R.string.infoAddDateTimeToMeeting, Toast.LENGTH_SHORT).show();
        } else {
            calendar.set(mYear, mMonth, mDay, mHours, mMinutes);
            mDate = calendar.getTime();

            if (checkIfRoomAvailable(mDate, selectedRoom)) {
                Meeting meeting = new Meeting(mDate, selectedRoom, subjet, participants);
                mMeetingRepository.addMeeting(meeting);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), R.string.infoMeetingRoom, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkIfRoomAvailable(Date date, Room selectedRoom) {
        Boolean available = true;
        List<Meeting> meetingList = mMeetingRepository.getMeetings();

        for (Meeting meeting : meetingList) {
            if (meeting.getRoom() == selectedRoom) {
                calendar.setTime(meeting.getDate());
                calendar.add(Calendar.MINUTE, -45);
                Date timeBefore = calendar.getTime();
                calendar.add(Calendar.MINUTE, 90);
                Date timeFinish = calendar.getTime();
                if (date.compareTo(timeBefore) == 1 && date.compareTo(timeFinish) == -1) {
                    available = false;
                }
            }
        }
        return available;
    }

    private void showContributorDialog() {
        DialogParticipantsFragment dialog = new DialogParticipantsFragment();
        dialog.addListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "DialogContributorSelectorFragment");


    }


    private void showTimePikerDialog() {
        DialogTimeFragment dialog = new DialogTimeFragment();
        dialog.addListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "DialogTimePikerFragment");

    }


    private void configureRoomSpinner() {
        List<Room> rooms = mMeetingRepository.getMeetingsRoomsList();
        mMeetingRoom.setAdapter(new ArrayAdapter<Room>(getContext()
                , android.R.layout.simple_spinner_dropdown_item, rooms));
        mMeetingRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoom = (Room) mMeetingRoom.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void showListContributor() {
        mContributorList.setVisibility(View.VISIBLE);
        String textmails = "";
        for (String mail : participants) {
            textmails += mail + "\n";
        }
        mContributorList.setText(textmails);
    }

    @Override
    public void onDialogDatePikerValidateClick(DialogDateFragment dialog) {
        DatePicker datePicker = (DatePicker) dialog.getDialog().findViewById(R.id.date_dp);
        mDay = datePicker.getDayOfMonth();
        mMonth = datePicker.getMonth();
        mYear = datePicker.getYear();
        dateFormated = String.format("%02d/%02d/%d", mDay, mMonth + 1, mYear);
        mDateSelectorBtn.setText(dateFormated);
    }


    @Override
    public void onDialogParticipantsValidateClick(DialogFragment dialog) {
        EditText contributor = dialog.getDialog().findViewById(R.id.contributor_edit_txt);
        CheckBox addContributor = dialog.getDialog().findViewById(R.id.add_contributor_checkbox);

        if (isEmailValid(contributor.getText().toString())) {
            participants.add(contributor.getText().toString());
            Toast.makeText(this.getContext(), R.string.contributorAddWithSuccess, Toast.LENGTH_SHORT).show();
            if (addContributor.isChecked()) {
                showContributorDialog();
            } else {
                showListContributor();
            }
        } else {
            Toast.makeText(this.getContext(), R.string.mailNotValide, Toast.LENGTH_SHORT).show();
            showContributorDialog();
        }
    }

    @Override
    public void onDialogCancelParticpants(DialogFragment dialog) {
        showListContributor();

    }


    @Override
    public void onDialogTimePikerValidateClick(DialogFragment dialog) {
        TimePicker time = (TimePicker) dialog.getDialog().findViewById(R.id.time_dp);
        mHours = time.getCurrentHour();
        mMinutes = time.getCurrentMinute();
        timeFormated = String.format("%02dh%02d", mHours, mMinutes);
        mMeetingSelectTime.setText(timeFormated);
        Toast.makeText(this.getContext(), R.string.choiceConfirmed, Toast.LENGTH_SHORT).show();
    }

}


