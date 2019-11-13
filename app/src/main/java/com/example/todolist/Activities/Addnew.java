package com.example.todolist.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolist.DataBase.AppDataBase;
import com.example.todolist.Models.TaskModel;
import com.example.todolist.Pickers.DatePickerFragment;
import com.example.todolist.Pickers.TimePickerFragment;
import com.example.todolist.R;

public class Addnew extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {
    Spinner spinner;
    EditText nametask;
    int timeH, timeM, dateD, dateM, dateY;
    AppDataBase appDataBase;
    TextView time_txt, date_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faddnew);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.priority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        appDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"test1").build();
        initviews();
    }
    void initviews(){
        nametask                = findViewById(R.id.new_taskname);
        time_txt                = findViewById(R.id.time_txt);
        date_txt                = findViewById(R.id.date_txt);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select Priority", Toast.LENGTH_SHORT).show();
    }

    public void addTime(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int H, int M) {
        timeH               = H;
        timeM               = M;
        time_txt.setText(H+":"+M);
    }

    public void addDate(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Y, int M, int D) {
        dateY               = Y;
        dateM               = M;
        dateD               = D;
        date_txt.setText(Y+"/"+M+"/"+D);
    }


    public class Insert extends AsyncTask<TaskModel,Void,Void> {

        @Override
        protected Void doInBackground(TaskModel... taskModels) {
            appDataBase.taskDao().insert(taskModels);
            return null;
        }
    }

    public void addtask(View view) {
        String taskname;
        int pr = 0;
        if(nametask.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Task Name", Toast.LENGTH_SHORT).show();
            return;
        }
            taskname = nametask.getText().toString();
        if(date_txt.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(time_txt.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show();
            return;
        }
        if(spinner.getSelectedItem().toString().equals("Priority")){
            Toast.makeText(this, "Select Priority", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(spinner.getSelectedItem().toString().equals("High")){
            pr = 2;
        }

        else if(spinner.getSelectedItem().toString().equals("Medium")){
            pr = 1;
        }
        else if(spinner.getSelectedItem().toString().equals("Low")){
            pr = 0;
        }

        TaskModel taskModel = new TaskModel(pr,taskname,dateD,dateM,dateY,timeH,timeM);
        new Insert().execute(taskModel);
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent goback = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(goback);
    }
}
