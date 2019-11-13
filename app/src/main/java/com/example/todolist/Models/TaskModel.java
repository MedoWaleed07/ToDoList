package com.example.todolist.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "priority")
    private int pri;
    @ColumnInfo(name = "task_name")
    private String taskname;
    @ColumnInfo(name = "dataD")
    private int dateD;
    @ColumnInfo(name = "dataM")
    private int dateM;
    @ColumnInfo(name = "dataY")
    private int dateY;
    @ColumnInfo(name = "timeH")
    private int timeH;
    @ColumnInfo(name = "timeM")
    private int timeM;


    @Ignore
    public TaskModel(int id, int pri, String taskname, int dateD, int dateM, int dateY, int timeH, int timeM) {
        this.id = id;
        this.pri = pri;
        this.taskname = taskname;
        this.dateD = dateD;
        this.dateM = dateM;
        this.dateY = dateY;
        this.timeH = timeH;
        this.timeM = timeM;
    }

    public TaskModel(int pri, String taskname, int dateD, int dateM, int dateY, int timeH, int timeM) {
        this.pri = pri;
        this.taskname = taskname;
        this.dateD = dateD;
        this.dateM = dateM;
        this.dateY = dateY;
        this.timeH = timeH;
        this.timeM = timeM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPri() {
        return pri;
    }

    public void setPri(int pri) {
        this.pri = pri;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public int getDateD() {
        return dateD;
    }

    public void setDateD(int dateD) {
        this.dateD = dateD;
    }

    public int getDateM() {
        return dateM;
    }

    public void setDateM(int dateM) {
        this.dateM = dateM;
    }

    public int getDateY() {
        return dateY;
    }

    public void setDateY(int dateY) {
        this.dateY = dateY;
    }

    public int getTimeH() {
        return timeH;
    }

    public void setTimeH(int timeH) {
        this.timeH = timeH;
    }

    public int getTimeM() {
        return timeM;
    }

    public void setTimeM(int timeM) {
        this.timeM = timeM;
    }
}