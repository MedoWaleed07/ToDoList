package com.example.todolist.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.Models.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TaskModel... taskModels);

    @Query("SELECT * FROM TaskModel WHERE priority = 0")
    List<TaskModel> getLow();
    @Query("SELECT * FROM TaskModel WHERE priority = 1")
    List<TaskModel> getMed();
    @Query("SELECT * FROM TaskModel WHERE priority = 2")
    List<TaskModel> getHigh();

    @Query("SELECT * FROM TaskModel WHERE priority = 0")
    TaskModel getLowTask();
    @Query("SELECT * FROM TaskModel WHERE priority = 1")
    TaskModel getMedTask();
    @Query("SELECT * FROM TaskModel WHERE priority = 2")
    TaskModel getHightask();

    @Update
    void update(TaskModel taskModel);

    @Delete
    void delete(TaskModel taskModel);

}
