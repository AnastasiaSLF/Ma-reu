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

public class DialogTimeFragment extends DialogFragment {

    public interface DialogTimePickerListener{
        void onDialogCancelParticpants(DialogFragment dialog);

        public void onDialogTimePikerValidateClick(DialogFragment dialog);
    }

    DialogTimePickerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_time,null))
                .setMessage("Heure de réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogTimePikerValidateClick(DialogTimeFragment.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogTimeFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void addListener(DialogTimePickerListener listener) {
        mListener = listener;
    }
}
