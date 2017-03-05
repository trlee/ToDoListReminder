package com.example.lenovoz50.todolistreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovoz50.todolistreminder.db.TaskItem;
import com.example.lenovoz50.todolistreminder.db.TaskList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Lenovo Z50 on 1/3/2017.
 */

public class TaskFragment extends Fragment {
    private static final String ARG_TASK_ID = "task_id";
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private TaskItem mTask;
    private EditText mTitleField;
    private Button mTimeButton;
    private Button mDateButton;

    public static TaskFragment newInstance(UUID taskID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskID);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID taskID = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = TaskList.get(getActivity()).getTask(taskID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        mTitleField = (EditText)v.findViewById(R.id.task_title);
        mTitleField.setText(mTask.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.task_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mTask.getTime());
                dialog.setTargetFragment(TaskFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mDateButton = (Button)v.findViewById(R.id.task_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDate());
                dialog.setTargetFragment(TaskFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            updateDate();
        }
        if (requestCode == REQUEST_TIME){
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTask.setTime(time);
            updateTime();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TaskList.get(getActivity()).updateTask(mTask);
    }

    private void updateDate() {
    String updatedDate = new SimpleDateFormat("yyyy-MM-dd").format(mTask.getDate());
    mDateButton.setText("Set Date (" + updatedDate + ")");
    }

    private void updateTime(){
        String updatedTime = new SimpleDateFormat("HH:mm:ss").format(mTask.getTime());
        mTimeButton.setText("Set Time (" + updatedTime + ")");
    }
}
