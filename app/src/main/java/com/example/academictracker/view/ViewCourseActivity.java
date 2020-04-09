package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.CourseViewModel;
import com.example.academictracker.viewmodel.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewCourseActivity extends AppCompatActivity {
    private int id;
    private Course course;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewMentorName;
    private TextView textViewMentorPhone;
    private TextView textViewMentorEmail;
    private TextView textViewCourseStatus;
    private CourseViewModel courseViewModel;
    public static final int EDIT_COURSE_REQUEST = 1;
    public static final int DELETE_COURSE_REQUEST = 2;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        Button buttonSetNotification = findViewById(R.id.button_view_course_set_notification);
        Button buttonViewCourseNote = findViewById(R.id.button_view_course_note);
        Button buttonEditCourse = findViewById(R.id.button_view_course_edit_course);
        Button buttonDeleteCourse = findViewById(R.id.button_view_course_delete_course);
        Button buttonViewCourseAssessments = findViewById(R.id.button_view_course_assessments);

        Intent intent = getIntent();
        id = intent.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
        textViewTitle = findViewById(R.id.course_view_title);
        textViewStartDate = findViewById(R.id.course_view_start_date);
        textViewEndDate = findViewById(R.id.course_view_end_date);
        textViewMentorName = findViewById(R.id.course_view_mentor_name);
        textViewMentorPhone = findViewById(R.id.course_view_mentor_phone);
        textViewMentorEmail = findViewById(R.id.course_view_mentor_email);
        textViewCourseStatus = findViewById(R.id.course_view_course_status);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getCourse(id).observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    calendar.setTimeInMillis(course.getCourseStartDate());
                    textViewTitle.setText(course.getCourseTitle());
                    textViewStartDate.setText((dateFormat.format(calendar.getTime())));
                    calendar.setTimeInMillis(course.getCourseEndDate());
                    textViewEndDate.setText((dateFormat.format(calendar.getTime())));
                    textViewMentorName.setText(course.getCourseMentorName());
                    textViewMentorPhone.setText(course.getCourseMentorPhone());
                    textViewMentorEmail.setText(course.getCourseMentorEmail());
                    textViewCourseStatus.setText(course.getCourseMentorEmail());
                }
            }
        });

        buttonSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseActivity.this, "Set Notification", Toast.LENGTH_SHORT).show();
            }
        });

        buttonViewCourseNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseActivity.this, "View Course Note", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseActivity.this, "Edit Course", Toast.LENGTH_SHORT).show();
            }
        });

        buttonViewCourseAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseActivity.this, "View Assessments", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseActivity.this, "Delete Course", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
