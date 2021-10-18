package com.example.mareu.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.Model.Room;
import com.example.mareu.R;

import java.util.List;

import static com.example.mareu.Service.DummyMeetingsGenerator.ROOM;

public class DialogPlaceFragment extends DialogFragment {



    public interface DialogPlaceSpinnerListener{
        public void onDialogPlaceSpinnerValidateClick(DialogFragment dialog);
    }

    DialogPlaceSpinnerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_room,null);
        builder.setView(v)
                .setMessage("Lieu de réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPlaceSpinnerValidateClick(DialogPlaceFragment.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogPlaceFragment.this.getDialog().cancel();
                    }
                });

        Spinner listRooms = (Spinner) v.findViewById(R.id.dialog_room_spinner_sp);
        List<Room> rooms = ROOM;
        listRooms.setAdapter(new ArrayAdapter<Room>(getContext()
                ,android.R.layout.simple_spinner_dropdown_item, rooms));
        listRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Room selectedRoom = (Room) listRooms.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return builder.create();
    }

    public void addListener(DialogPlaceFragment.DialogPlaceSpinnerListener listener) {
        mListener = listener;

    }
}
