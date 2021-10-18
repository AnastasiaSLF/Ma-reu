package com.example.mareu;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.mareu.Dialogs.DialogDateFragment;
import com.example.mareu.Dialogs.DialogPlaceFragment;
import com.example.mareu.Model.Room;
import com.example.mareu.Repository.MeetingRepository;
import com.example.mareu.Service.MeetingsApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MeetingFragment extends Fragment implements DialogPlaceFragment.DialogPlaceSpinnerListener, DialogDateFragment.DialogDatePickerListener {

    private MeetingRepository mMeetingRepository;
    private MeetingRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton restoreDataFab;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingRepository = ((BaseActivity) getActivity()).getMeetingRepository();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.meeting_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        configureFABRestoreData();
        initList();
        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.filter_by_date_item:
                showDatePikerDialog();
                return true;
            case R.id.filter_by_place_item:
                showSpinnerPlaceDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSpinnerPlaceDialog() {
        DialogPlaceFragment dialog = new DialogPlaceFragment();
        dialog.addListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "DialogPlaceFragment");
    }

    private void showDatePikerDialog() {
        DialogDateFragment dialog = new DialogDateFragment();
        dialog.addListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "DialogPlaceFragment");
    }


    private void initList() {
        adapter = new MeetingRecyclerViewAdapter(mMeetingRepository.getMeetings());
        mRecyclerView.setAdapter(adapter);
        restoreDataFab.setVisibility(View.INVISIBLE);
    }

    private void configureFABRestoreData() {
        restoreDataFab = getActivity().findViewById(R.id.restore_fab);
        restoreDataFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initList();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mMeetingRepository.deleteMeeting(event.mMeeting);
        initList();
    }



    private void filterListByDate(String dateString){
        adapter.updateList(mMeetingRepository.filterByDate(dateString));
        mRecyclerView.setAdapter(adapter);
        restoreDataFab.setVisibility(View.VISIBLE);
    }

    private void filterListByPlace(String place){
        adapter.updateList(mMeetingRepository.filterByPlace(place));
        mRecyclerView.setAdapter(adapter);
        restoreDataFab.setVisibility(View.VISIBLE);
    }



    @Override
    public void onDialogDatePikerValidateClick(DialogDateFragment dialogDatePickerFragment) {
        DatePicker datePicker = (DatePicker) dialogDatePickerFragment.getDialog().findViewById(R.id.date_dp);
        int mDay = datePicker.getDayOfMonth();
        int mMonth = datePicker.getMonth();
        int mYear = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear,mMonth,mDay);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        filterListByDate(dateString);
    }

    @Override
    public void onDialogPlaceSpinnerValidateClick(DialogFragment dialog) {
        Spinner spinner = (Spinner) dialog.getDialog().findViewById(R.id.dialog_room_spinner_sp);
        Room room = (Room) spinner.getSelectedItem();
        filterListByPlace(room.toString());
    }
}