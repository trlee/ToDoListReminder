package com.example.lenovoz50.todolistreminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovoz50.todolistreminder.db.TaskItem;
import com.example.lenovoz50.todolistreminder.db.TaskList;

import java.util.List;
import java.util.UUID;

/**
 * Created by Lenovo Z50 on 4/3/2017.
 */

public class TaskViewPagerActivity extends AppCompatActivity {
    private static final String EXTRA_TASK_ID = "com.example.lenovoz50.todolistreminder.task_id";
    private ViewPager mViewPager;
    private List<TaskItem> mTasks;

    public static Intent newIntent(Context packageContext, UUID taskID) {
        Intent intent = new Intent(packageContext, TaskViewPagerActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_viewpager);

        UUID taskID = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_task_pager_view_pager);
        mTasks = TaskList.get(this).getTasks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TaskItem task = mTasks.get(position);
                return TaskFragment.newInstance(task.getID());
            }

            @Override
            public int getCount() {
                return mTasks.size();
            }
        });

        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getID().equals(taskID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
