package com.example.lenovoz50.todolistreminder.db;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Lenovo Z50 on 28/2/2017.
 */

public class TaskItem {
    private UUID mID;
    private Date mTime;
    private String mTitle;
    private Date mDate;

    public TaskItem(){
        this(UUID.randomUUID());
    }

    public TaskItem(UUID id){
        mID=id;
        mTime = new Date();
        mDate = new Date();
    }

    public UUID getID(){ return mID; }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mtitle) {
        mTitle = mtitle;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }
}
