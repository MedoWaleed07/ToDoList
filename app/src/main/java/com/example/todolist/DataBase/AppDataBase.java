package com.example.todolist.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todolist.Models.TaskModel;

@Database(entities = TaskModel.class , version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
