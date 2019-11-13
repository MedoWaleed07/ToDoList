package com.example.todolist.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.todolist.Adapters.TaskAdapter;
import com.example.todolist.DataBase.AppDataBase;
import com.example.todolist.Activities.Edit_Activity;
import com.example.todolist.Models.TaskModel;
import com.example.todolist.R;

import java.util.List;

public class Low_Fragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    View view;
    AppDataBase appDataBase;
    DividerItemDecoration dividerItemDecoration;
    TaskAdapter taskAdapter;
    List<TaskModel> task;
    TaskModel taskaya;
    ColorDrawable deleteBackround, updataBackround;
    Drawable deleteIcon,updateIcon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.low_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView            = view.findViewById(R.id.recycler);
        layoutManager           = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dividerItemDecoration   = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        deleteBackround         = new ColorDrawable(Color.parseColor("#FF0000"));
        updataBackround         = new ColorDrawable(Color.parseColor("#00FF00"));
        deleteIcon              = ContextCompat.getDrawable(getContext(),R.drawable.ic_delete_black_24dp);
        updateIcon              = ContextCompat.getDrawable(getContext(),R.drawable.ic_edit);
        appDataBase             = Room.databaseBuilder(getContext(),AppDataBase.class,"test1").build();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you Sure to delete this")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new deleteData().execute(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                                Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new getData().execute();
                            }
                        })
                        .show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemview = viewHolder.itemView;
                deleteBackround.setBounds(itemview.getLeft(),itemview.getTop(),Float.floatToIntBits(dX),itemview.getBottom());
                deleteBackround.draw(c);
                int iconMargin = (itemview.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                deleteIcon.setBounds(itemview.getLeft()+iconMargin,itemview.getTop()+iconMargin,itemview.getLeft()+iconMargin+deleteIcon.getIntrinsicWidth(),itemview.getBottom()-iconMargin);
                c.clipRect(itemview.getLeft(),itemview.getTop(),Float.floatToIntBits(dX),itemview.getBottom());
                deleteIcon.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        new getData().execute();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskaya                 = taskAdapter.getTaskAt(viewHolder.getAdapterPosition());
                String name             = taskaya.getTaskname();
                int id                  = taskaya.getId();
                int edit_task_dateY     = taskaya.getDateY();
                int edit_task_dateM     = taskaya.getDateM();
                int edit_task_dateD     = taskaya.getDateD();
                int edit_task_timeH     = taskaya.getTimeH();
                int edit_task_timeM     = taskaya.getTimeM();
                Intent edit             = new Intent(getContext(), Edit_Activity.class);
                edit.putExtra("name",name);
                edit.putExtra("ID",id);
                edit.putExtra("dateY",edit_task_dateY);
                edit.putExtra("dateM",edit_task_dateM);
                edit.putExtra("dateD",edit_task_dateD);
                edit.putExtra("timeH",edit_task_timeH);
                edit.putExtra("timeM",edit_task_timeM);
                edit.putExtra("priority","0");
                startActivity(edit);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemview = viewHolder.itemView;
                updataBackround.setBounds(itemview.getRight(),itemview.getTop(),Float.floatToIntBits(dX),itemview.getBottom());
                updataBackround.draw(c);
                int iconMargin = (itemview.getHeight() - updateIcon.getIntrinsicHeight()) / 2;
                updateIcon.setBounds(itemview.getRight() - iconMargin - updateIcon.getIntrinsicWidth(), itemview.getTop()+iconMargin, itemview.getRight() - iconMargin, itemview.getBottom() - iconMargin);
                c.clipRect(itemview.getRight() - iconMargin - updateIcon.getIntrinsicWidth(), itemview.getTop()+iconMargin, itemview.getRight() - iconMargin, itemview.getBottom() - iconMargin);
                updateIcon.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
    }


    public class getData extends AsyncTask<Void, Void, List<TaskModel>>{

        @Override
        protected List<TaskModel> doInBackground(Void... voids) {
            task = appDataBase.taskDao().getLow();
            return task;
        }

        @Override
        protected void onPostExecute(List<TaskModel> taskModels) {
            taskAdapter = new TaskAdapter(taskModels);
            recyclerView.setAdapter(taskAdapter);
        }
    }


    public class deleteData extends AsyncTask<TaskModel, Void,List<TaskModel>>{

        @Override
        protected List<TaskModel> doInBackground(TaskModel... taskModels) {
            appDataBase.taskDao().delete(taskModels[0]);
            return appDataBase.taskDao().getLow();
        }

        @Override
        protected void onPostExecute(List<TaskModel> taskModels) {
            taskAdapter = new TaskAdapter(taskModels);
            recyclerView.setAdapter(taskAdapter);
        }
    }
}
