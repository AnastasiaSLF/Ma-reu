package com.example.mareu.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

public class DialogParticipantsFragment extends DialogFragment {

        public interface DialogParticipantsListener{
            public void onDialogParticipantsValidateClick(DialogFragment dialog);
            public void onDialogCancelParticpants(DialogFragment dialog);
        }

        DialogParticipantsListener mListener;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(R.layout.dialog_participants)
                    .setTitle("Ajouter des participants")
                    .setNegativeButton("annuler", (dialog, which) -> mListener.onDialogCancelParticpants(DialogParticipantsFragment.this))
                    .setPositiveButton("Valider", (dialog, which) -> { mListener.onDialogParticipantsValidateClick(DialogParticipantsFragment.this);
                        dialog.dismiss();
                    });

            return builder.create();
        }

        public void addListener(DialogParticipantsFragment.DialogParticipantsListener listener) {
            mListener = listener;

        }
    }


