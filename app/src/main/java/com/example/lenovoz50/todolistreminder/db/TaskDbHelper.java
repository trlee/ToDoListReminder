package com.example.lenovoz50.todolistreminder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo Z50 on 28/2/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper{
    public TaskDbHelper(Context context){
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_UUID + ", " +
                TaskContract.TaskEntry.COL_TASK_TITLE + ", " +
                TaskContract.TaskEntry.COL_TASK_DATE + ", " +
                TaskContract.TaskEntry.COL_TASK_TIME + " );";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
