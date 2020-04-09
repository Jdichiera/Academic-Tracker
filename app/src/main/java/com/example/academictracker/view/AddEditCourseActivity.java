package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.adapters.CourseStatusAdapter;
import com.example.academictracker.adapters.CourseStatusItem;

import java.util.ArrayList;
import java.util.Calendar;

public class AddEditCourseActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.academictracker.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";
    public static final String EXTRA_START_DATE = "com.example.academictracker.view.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.academictracker.view.EXTRA_END_DATE";
    public static final String EXTRA_MENTOR_NAME = "com.example.academictracker.view.EXTRA_MENTOR_NAME";
    public static final String EXTRA_MENTOR_EMAIL = "com.example.academictracker.view.EXTRA_MENTOR_EMAIL";
    public static final String EXTRA_MENTOR_PHONE = "com.example.academictracker.view.EXTRA_MENTOR_PHONE";
    public static final String EXTRA_COURSE_STATUS = "com.example.academictracker.view.EXTRA_COURSE_STATUS";
    public static final String EXTRA_TERM_ID = "com.example.academictracker.view.EXTRA_TERM_ID";

    DatePicker datePickerStartDate;
    DatePicker datePickerEndDate;
    EditText editTextTitle;
    EditText editTextMentorName;
    EditText editTextMentorEmail;
    EditText editTextMentorPhone;
    private ArrayList<CourseStatusItem> courseStatusList;
    private CourseStatusAdapter courseStatusAdapter;
    Spinner spinnerCourseStatus;
    String courseStatus;
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);
        initList();
        spinnerCourseStatus = findViewById(R.id.course_add_status_spinner);
        courseStatusAdapter = new CourseStatusAdapter(this, courseStatusList);
        spinnerCourseStatus.setAdapter(courseStatusAdapter);

        spinnerCourseStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseStatusItem clickedStatus = (CourseStatusItem) parent.getItemAtPosition(position);
                String courseStatusName = clickedStatus.getCourseStatusName();
                courseStatus = clickedStatus.getCourseStatusName();
                Toast.makeText(AddEditCourseActivity.this, courseStatus, Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddEditCourseActivity.this, courseStatusName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextTitle = findViewById(R.id.course_add_title);
        datePickerStartDate = findViewById(R.id.course_add_start);
        datePickerEndDate = findViewById(R.id.course_add_end);
        editTextMentorName = findViewById(R.id.course_add_mentor_name);
        editTextMentorEmail = findViewById(R.id.course_add_mentor_email);
        editTextMentorPhone = findViewById(R.id.course_add_mentor_phone);

        Intent intent = getIntent();
        termId = intent.getIntExtra(EXTRA_TERM_ID, -1);
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Course");
            Calendar calendar = Calendar.getInstance();
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextMentorName.setText(intent.getStringExtra(EXTRA_MENTOR_NAME));
            editTextMentorEmail.setText(intent.getStringExtra(EXTRA_MENTOR_EMAIL));
            editTextMentorPhone.setText(intent.getStringExtra(EXTRA_MENTOR_PHONE));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_START_DATE, 0));
            datePickerStartDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_END_DATE, 0));
            datePickerEndDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            int spinnerPosition = 0;
            for (int i = 0; i < courseStatusList.size(); i++) {
                if (courseStatusList.get(i).getCourseStatusName().equals(intent.getStringExtra(EXTRA_COURSE_STATUS))) {
                    spinnerPosition = i;
                }
            }
            spinnerCourseStatus.setSelection(spinnerPosition);
        } else {
            setTitle("Add Course");
        }
    }

    public void saveCourse(View view) {
        long startDateLong;
        long endDateLong;
        String title = editTextTitle.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePickerStartDate.getYear(), datePickerStartDate.getMonth(), datePickerStartDate.getDayOfMonth());
        startDateLong = calendar.getTimeInMillis();
        calendar.set(datePickerEndDate.getYear(), datePickerEndDate.getMonth(), datePickerEndDate.getDayOfMonth());
        endDateLong = calendar.getTimeInMillis();
        String mentorName = editTextMentorName.getText().toString();
        String mentorEmail = editTextMentorEmail.getText().toString();
        String mentorPhone = editTextMentorPhone.getText().toString();
//        String courseStatus = spinnerCourseStatus.getSelectedItem().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, startDateLong);
        data.putExtra(EXTRA_END_DATE, endDateLong);
        data.putExtra(EXTRA_MENTOR_NAME, mentorName);
        data.putExtra(EXTRA_MENTOR_EMAIL, mentorEmail);
        data.putExtra(EXTRA_MENTOR_PHONE, mentorPhone);
        data.putExtra(EXTRA_COURSE_STATUS, courseStatus);
        data.putExtra(EXTRA_TERM_ID, termId);

        // If we are in an update scenario - pass the ID so we know that we are updating a term
        // We pass a -1 default in
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void initList() {
        this.courseStatusList = new ArrayList<>();
        this.courseStatusList.add(new CourseStatusItem("Not Started"));
        this.courseStatusList.add(new CourseStatusItem("In Progress"));
        this.courseStatusList.add(new CourseStatusItem("Completed"));
        this.courseStatusList.add(new CourseStatusItem("Dropped"));
    }
}
