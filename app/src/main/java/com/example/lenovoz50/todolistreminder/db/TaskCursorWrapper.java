package com.example.lenovoz50.todolistreminder.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Lenovo Z50 on 3/3/2017.
 */

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public TaskItem getTask(){
        String UUIDString = getString(getColumnIndex(TaskContract.TaskEntry.COL_TASK_UUID));
        String title = getString(getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE));
        long date = getLong(getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE));
        long time = getLong(getColumnIndex(TaskContract.TaskEntry.COL_TASK_TIME));
        TaskItem task = new TaskItem(UUID.fromString(UUIDString));
        task.setTitle(title);
        task.setTime(new Date(time));
        task.setDate(new Date(date));
        return task;
    }
}
