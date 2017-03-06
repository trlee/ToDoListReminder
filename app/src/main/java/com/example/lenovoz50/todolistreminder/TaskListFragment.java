package com.example.lenovoz50.todolistreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovoz50.todolistreminder.db.TaskItem;
import com.example.lenovoz50.todolistreminder.db.TaskList;


import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Lenovo Z50 on 4/3/2017.
 */

public class TaskListFragment extends Fragment {

    private RecyclerView mTaskRecyclerView;
    private TaskListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_menu, container, false);
        mTaskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                TaskItem task = new TaskItem();
                TaskList.get(getActivity()).addTask(task);
                Intent intent = TaskViewPagerActivity.newIntent(getActivity(), task.getID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        TaskList taskList = TaskList.get(getActivity());
        List<TaskItem> tasks = taskList.getTasks();

        if (mAdapter == null){
            mAdapter = new TaskListAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private Button mDoneButton;
        private TaskItem mTask;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.task_title_textView);
            mDateTextView = (TextView) itemView.findViewById(R.id.task_date_textView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.task_time_textView);
            mDoneButton = (Button) itemView.findViewById(R.id.task_delete);
        }
        @Override
        public void onClick(View v) {
            Intent intent = TaskViewPagerActivity.newIntent(getActivity(), mTask.getID());
            startActivity(intent);
        }
        public void bindTask(final TaskItem task){
            mTask = task;
            String updatedDate = new SimpleDateFormat("yyyy-MM-dd").format(mTask.getDate());
            String updatedTime = new SimpleDateFormat("HH:mm:ss").format(mTask.getTime());

            mTitleTextView.setText(mTask.getTitle());
            mDateTextView.setText(updatedDate);
            mTimeTextView.setText(updatedTime);
            mDoneButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    View parent = (View) v.getParent();
                    TaskList.get(getActivity()).deleteTask(task);
                    updateUI();
                }
            });
        }
    }

    private class TaskListAdapter extends RecyclerView.Adapter<TaskHolder>{

        private List<TaskItem> mTasks;

        public TaskListAdapter(List<TaskItem> tasks){ mTasks = tasks; }
        public void setTasks(List<TaskItem> tasks){ mTasks = tasks; }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.task_item, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            TaskItem task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
