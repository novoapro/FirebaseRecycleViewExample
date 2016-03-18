package com.manpdev.firebaseexample.task;

import java.util.Date;

/**
 * Created by novoa on 3/17/16.
 */
public class TaskModel {

    private String name;
    private boolean critical;
    private Date date;
    private String notes;


    public TaskModel() {
    }

    public TaskModel(String name, boolean critical, Date date, String notes) {
        this.name = name;
        this.critical = critical;
        this.date = date;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
