package com.example.todolist.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Models.TaskModel;
import com.example.todolist.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {
    List<TaskModel> taskModels;
    public TaskAdapter(List<TaskModel> taskModels) {
        this.taskModels = taskModels;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TaskModel taskModel = taskModels.get(position);
        String taskname;
        int timeM,timeH, dateD, dateM, dateY;
        int pr;
        taskname            = taskModel.getTaskname();
        dateD               = taskModel.getDateD();
        dateM               = taskModel.getDateM();
        dateY               = taskModel.getDateY();
        timeM               = taskModel.getTimeM();
        timeH               = taskModel.getTimeH();
        pr                  = taskModel.getPri();
        if(pr == 2){
            holder.timelayout.setBackgroundResource(R.drawable.high_pr);
        }
        if(pr == 1){
            holder.timelayout.setBackgroundResource(R.drawable.med_pr);
        }
        if(pr == 0){
            holder.timelayout.setBackgroundResource(R.drawable.low_pr);
        }
        holder.taskname.setText(taskname);
        holder.date.setText(dateY+"-"+dateM+"-"+dateD);
        holder.time.setText(timeH+":"+timeM);
    }
    public TaskModel getTaskAt(int position){
        return taskModels.get(position);
    }

    @Override
    public int getItemCount() {
        if(taskModels == null){
            return 0;
        }
        return taskModels.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView date,time,taskname;
        LinearLayout timelayout;
        public VH(@NonNull View itemView) {
            super(itemView);
            taskname            = itemView.findViewById(R.id.taskname);
            date                = itemView.findViewById(R.id.date);
            time                = itemView.findViewById(R.id.time);
            timelayout          = itemView.findViewById(R.id.time_layout);
        }
    }

}
