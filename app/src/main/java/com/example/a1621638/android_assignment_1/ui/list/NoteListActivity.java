package com.example.a1621638.android_assignment_1.ui.list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.a1621638.android_assignment_1.R;
import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.ui.editor.NoteEditActivity;
import com.example.a1621638.android_assignment_1.ui.util.DatePickerDialogFragment;
import com.example.a1621638.android_assignment_1.ui.util.TimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class NoteListActivity extends AppCompatActivity {

    private Date reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void startReminder() {
        final Calendar calendar = Calendar.getInstance();

        final TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.create(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        reminder = calendar.getTime();
                    }
                }
        );

        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.create(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        timePickerDialogFragment.show(getSupportFragmentManager(), "timePicker");
                    }
                }
        );

        datePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public Date getReminderDate() {
        return reminder;
    }
}
