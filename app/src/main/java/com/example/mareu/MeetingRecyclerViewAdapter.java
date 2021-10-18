package com.example.mareu;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetings;

    private String textmails = "";

    public MeetingRecyclerViewAdapter(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<Meeting> meetingList){
        this.mMeetings = meetingList;
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mPastil.getDrawable().setTint(Color.parseColor(meeting.getRoom().getColor()));
        holder.mTitle.setText(meeting.getSubject() + " - " + meeting.getRoom() + " - " + meeting.getTimeFormated());
        holder.mPastil.getDrawable();
        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));


        List<String> participants = meeting.getMailContributor();
        for (int i = 0; i < participants.size(); i++) {
            textmails += participants.get(i);
            if (i == participants.size()-1){
                textmails += ".";
            }else {
                textmails += " , ";
            }
        }

        holder.mMails.setText(textmails);
    }



    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_title)
        public TextView mTitle;
        @BindView(R.id.item_list_pastil)
        public ImageView mPastil;
        @BindView(R.id.item_mail_list)
        public TextView mMails;
        @BindView(R.id.delete_item_iv)
        public ImageButton mDeleteButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}

