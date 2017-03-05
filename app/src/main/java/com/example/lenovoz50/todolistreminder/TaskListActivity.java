package com.example.lenovoz50.todolistreminder;

import android.support.v4.app.Fragment;

/**
 * Created by Lenovo Z50 on 4/3/2017.
 */

public class TaskListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){ return new TaskListFragment();}
}
