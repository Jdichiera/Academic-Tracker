package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academictracker.R;

import java.util.Calendar;

public class AddEditCourseActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.academictracker.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";
    public static final String EXTRA_START_DATE = "com.example.academictracker.view.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.academictracker.view.EXTRA_END_DATE";
    public static final String EXTRA_MENTOR_NAME = "com.example.academictracker.view.EXTRA_MENTOR_NAME";
    public static final String EXTRA_MENTOR_EMAIL = "com.example.academictracker.view.EXTRA_MENTOR_EMAIL";
    public static final String EXTRA_MENTOR_NUMBER = "com.example.academictracker.view.EXTRA_MENTOR_NUMBER";
    public static final String EXTRA_COURSE_STATUS = "com.example.academictracker.view.EXTRA_COURSE_STATUS";

    DatePicker datePickerStartDate = findViewById(R.id.course_add_start);
    DatePicker datePickerEndDate = findViewById(R.id.course_add_end);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);
        EditText editTextTitle = findViewById(R.id.course_add_title);
        datePickerStartDate = findViewById(R.id.course_add_start);
        datePickerEndDate = findViewById(R.id.course_add_end);
        EditText editTextMentorName = findViewById(R.id.course_add_mentor_name);
        EditText editTextMentorEmail = findViewById(R.id.course_add_mentor_email);
        EditText editTextMentorNumber = findViewById(R.id.course_add_mentor_phone);
        EditText editTextCourseStatus = findViewById(R.id.course_add_status);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Course");
            Calendar calendar = Calendar.getInstance();
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextMentorName.setText(intent.getStringExtra(EXTRA_MENTOR_NAME));
            editTextMentorEmail.setText(intent.getStringExtra(EXTRA_MENTOR_EMAIL));
            editTextMentorNumber.setText(intent.getStringExtra(EXTRA_MENTOR_NUMBER));
            editTextCourseStatus.setText(intent.getStringExtra(EXTRA_COURSE_STATUS));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_START_DATE, 0));
            datePickerStartDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_END_DATE, 0));
            datePickerEndDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            setTitle("Add Course");
        }
    }

    public void saveTerm() {
        Toast.makeText(this, "Save Course", Toast.LENGTH_SHORT).show();
    }
}
