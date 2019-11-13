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

public class Edit_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {

    EditText taskname;
    TextView taskdate,tasktime;
    Spinner spinner;
    AppDataBase appDataBase;
    String name,date,time,pr;
    int timeH, timeM, dateD, dateM, dateY, priority,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        spinner = findViewById(R.id.edit_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.priority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        appDataBase     = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"test1").build();
        initviews();
        name                = getIntent().getStringExtra("name");
        id                  = getIntent().getIntExtra("ID",id);
        dateY               = getIntent().getIntExtra("dateY",0);
        dateM               = getIntent().getIntExtra("dateM",0);
        dateD               = getIntent().getIntExtra("dateD",0);
        timeH               = getIntent().getIntExtra("timeH",0);
        timeM               = getIntent().getIntExtra("timeM",0);
        pr                  = getIntent().getStringExtra("priority");
        date                = dateY + "/" + dateM + "/" + dateD;
        time                = timeH + ":" + timeM;
            taskname.setText(name);
            taskdate.setText(date);
            tasktime.setText(time);
            if(pr.equals("2")){
                spinner.setSelection(1);
            }
            else if(pr.equals("1")){
                spinner.setSelection(2);
            }
            else if(pr.equals("0")){
                spinner.setSelection(3);
            }
        }
    void initviews(){
        taskname                = findViewById(R.id.edit_task_name);
        tasktime                = findViewById(R.id.edit_time_txt);
        taskdate                = findViewById(R.id.edit_date_txt);
    }

    public void edittask(View view) {
        String name;
        if(taskname.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Task Name", Toast.LENGTH_SHORT).show();
            return;
        }
        name = taskname.getText().toString();
        if(taskdate.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tasktime.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show();
            return;
        }
        if(spinner.getSelectedItem().toString().equals("Priority")){
            Toast.makeText(this, "Select Priority", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(spinner.getSelectedItem().toString().equals("High")){
            priority = 2;
        }

        else if(spinner.getSelectedItem().toString().equals("Medium")){
            priority = 1;
        }
        else if(spinner.getSelectedItem().toString().equals("Low")){
            priority = 0;
        }


        TaskModel taskModel = new TaskModel(id,priority,name,dateD,dateM,dateY,timeH,timeM);
        new UpdateData().execute(taskModel);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void editDate(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Y, int M, int D) {
        dateY               = Y;
        dateM               = M;
        dateD               = D;
        taskdate.setText(Y+"/"+M+"/"+D);
    }

    public void editTime(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int H, int M) {
        timeH               = H;
        timeM               = M;
        tasktime.setText(H+":"+M);
    }
    class UpdateData extends AsyncTask<TaskModel,Void,Void>{

        @Override
        protected Void doInBackground(TaskModel... taskModels) {
            appDataBase.taskDao().update(taskModels[0]);
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
