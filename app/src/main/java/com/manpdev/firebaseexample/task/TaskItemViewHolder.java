package com.manpdev.firebaseexample.task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manpdev.firebaseexample.R;

import java.text.DateFormat;
import java.util.Date;

public class TaskItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mNotes;
    private TextView mDate;
    private ImageView mCritical;
    private ViewGroup mContainer;

    public TaskItemViewHolder(View itemView) {
        super(itemView);
        this.mName = (TextView) itemView.findViewById(R.id.tv_task_name);
        this.mNotes = (TextView) itemView.findViewById(R.id.tv_task_notes);
        this.mCritical = (ImageView) itemView.findViewById(R.id.iv_critical_icon);
        this.mDate = (TextView) itemView.findViewById(R.id.tv_task_date);
        this.mContainer = (ViewGroup) itemView.findViewById(R.id.task_item_container);
    }

    public void setName(String name){
        this.mName.setText(name);
    }

    public void setNotes(String notes){
        this.mNotes.setText(notes);
    }

    public void setCritical(boolean critical){
        if(critical)
            this.mCritical.setVisibility(View.VISIBLE);
        else
            this.mCritical.setVisibility(View.INVISIBLE);
    }

    public void setDate(Date date){
        this.mDate.setText(DateFormat.getInstance().format(date));
    }

    public void setListener(final TaskItemListener listener){
        this.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectedTask(TaskItemViewHolder.this);
            }
        });
    }
}
