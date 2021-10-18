package com.example.mareu.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

public class DialogDateFragment extends DialogFragment {

    public interface DialogDatePickerListener{
        public void onDialogDatePikerValidateClick(DialogDateFragment dialogDatePickerFragment);
    }

    DialogDatePickerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_date,null))
                .setMessage("Date de réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogDatePikerValidateClick(DialogDateFragment.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogDateFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void addListener(DialogDateFragment.DialogDatePickerListener listener) {
        mListener = listener;
    }
}
