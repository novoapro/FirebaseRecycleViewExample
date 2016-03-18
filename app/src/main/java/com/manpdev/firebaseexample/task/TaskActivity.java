package com.manpdev.firebaseexample.task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.annotations.Nullable;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.manpdev.firebaseexample.R;

import java.util.Calendar;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity implements TaskItemListener {

    private Firebase mRef;
    private FirebaseRecyclerAdapter<TaskModel, TaskItemViewHolder> mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_task);

        this.mRef = new Firebase(getString(R.string.FIREBASE_TASK_REF));

        initAdapter();

        findViewById(R.id.bt_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog(null, null);
            }
        });
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTaskAdapter.cleanup();
    }

    @Override
    public void onSelectedTask(TaskItemViewHolder holder) {
        TaskModel task = mTaskAdapter.getItem(holder.getAdapterPosition());
        showTaskDialog(task, mTaskAdapter.getRef(holder.getAdapterPosition()));
    }

    private void initAdapter() {
        this.mTaskAdapter = new FirebaseRecyclerAdapter<TaskModel, TaskItemViewHolder>(
                TaskModel.class,
                R.layout.item_task,
                TaskItemViewHolder.class,
                this.mRef) {
            @Override
            protected void populateViewHolder(TaskItemViewHolder taskItemViewHolder, TaskModel taskModel, int i) {
                taskItemViewHolder.setName(taskModel.getName());
                taskItemViewHolder.setNotes(taskModel.getNotes());
                taskItemViewHolder.setCritical(taskModel.isCritical());
                taskItemViewHolder.setDate(taskModel.getDate());
                taskItemViewHolder.setListener(TaskActivity.this);
            }
        };
    }

    private void initRecyclerView() {
        RecyclerView mRecView = (RecyclerView) findViewById(R.id.rv_task_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(TaskActivity.this);
        mRecView.setLayoutManager(layoutManager);
        mRecView.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Firebase itemRef = mTaskAdapter.getRef(viewHolder.getAdapterPosition());
                itemRef.removeValue();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecView);

        mRecView.setAdapter(mTaskAdapter);
    }

    private void showTaskDialog(@Nullable TaskModel task, @Nullable final Firebase ref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        final View root = getLayoutInflater().inflate(R.layout.add_task_layout, null);
        final EditText nameField = (EditText) root.findViewById(R.id.et_name);
        final EditText noteField = (EditText) root.findViewById(R.id.et_notes);
        final CheckBox criticalCheck = (CheckBox) root.findViewById(R.id.critical_ch);

        final TaskModel model;

        if (task != null) {
            model = task;
            nameField.setText(task.getName());
            noteField.setText(task.getNotes());
            criticalCheck.setChecked(task.isCritical());
        } else {
            model = new TaskModel();
        }

        builder.setView(root)
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(getString(R.string.save_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setName(nameField.getText().toString());
                        model.setNotes(noteField.getText().toString());
                        model.setCritical(criticalCheck.isChecked());

                        if (ref == null) {
                            model.setDate(Calendar.getInstance().getTime());
                            Firebase refNew = mRef.push();
                            refNew.setValue(model);
                            Toast.makeText(TaskActivity.this, String.format(Locale.US, "Created with Id : %s", refNew.getKey()), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(TaskActivity.this, String.format(Locale.US, "Updated : %s", ref.getKey()), Toast.LENGTH_LONG).show();
                            ref.setValue(model);
                        }
                    }
                });

        builder.create().show();
    }
}
