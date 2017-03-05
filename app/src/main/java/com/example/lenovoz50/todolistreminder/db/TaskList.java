package com.example.lenovoz50.todolistreminder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lenovo Z50 on 1/3/2017.
 */

public class TaskList {
    private static TaskList sTaskList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TaskList get(Context context){
        if (sTaskList == null){
            sTaskList = new TaskList(context);
        }
        return sTaskList;
    }

    private TaskList(Context context){
        mContext =  context.getApplicationContext();
        mDatabase = new TaskDbHelper(mContext).getWritableDatabase();
    }

    public void addTask(TaskItem t){
        ContentValues values = getContentValues(t);
        mDatabase.insert(TaskContract.TaskEntry.TABLE, null, values);
    }

    public void deleteTask(TaskItem t){
        String UUIDString = t.getID().toString();
        ContentValues values = getContentValues(t);
        mDatabase.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry.COL_TASK_UUID + " = ?", new String[]{UUIDString});
    }

    public List<TaskItem> getTasks() {
        List<TaskItem> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTask(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    private static ContentValues getContentValues(TaskItem task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_UUID, task.getID().toString());
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task.getTitle());
        values.put(TaskContract.TaskEntry.COL_TASK_TIME, task.getTime().getTime());
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, task.getDate().getTime());
        return values;
    }

    public TaskItem getTask(UUID id){
        TaskCursorWrapper cursor = queryTask(TaskContract.TaskEntry.COL_TASK_UUID + " = ?",
                new String[]{id.toString()});
        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    public void updateTask(TaskItem task){
        if (task.getTitle() == null){
            deleteTask(task);
        }
        String UUIDString = task.getID().toString();
        ContentValues values = getContentValues(task);
        mDatabase.update(TaskContract.TaskEntry.TABLE, values, TaskContract.TaskEntry.COL_TASK_UUID + " = ?", new String[]{UUIDString});

    }

    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(TaskContract.TaskEntry.TABLE, null, whereClause, whereArgs, null, null, null);
        return new TaskCursorWrapper(cursor);
    }

}
