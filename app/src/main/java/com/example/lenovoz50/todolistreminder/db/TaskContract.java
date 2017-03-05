package com.example.lenovoz50.todolistreminder.db;

import android.provider.BaseColumns;

import java.util.UUID;

/**
 * Created by Lenovo Z50 on 28/2/2017.
 */

public class TaskContract {
    public static final String DB_NAME = "com.example.lenovoz50.todolistreminder.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_UUID = "UUID";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DATE = "date";
        public static final String COL_TASK_TIME = "time";
    }
}
