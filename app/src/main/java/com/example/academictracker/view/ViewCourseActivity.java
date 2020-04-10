package com.example.academictracker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewCourseActivity extends AppCompatActivity {
    private int courseId;
    private int termId;
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
        courseId = intent.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
        termId = intent.getIntExtra(AddEditCourseActivity.EXTRA_TERM_ID, -1);
        textViewTitle = findViewById(R.id.course_view_title);
        textViewStartDate = findViewById(R.id.course_view_start_date);
        textViewEndDate = findViewById(R.id.course_view_end_date);
        textViewMentorName = findViewById(R.id.course_view_mentor_name);
        textViewMentorPhone = findViewById(R.id.course_view_mentor_phone);
        textViewMentorEmail = findViewById(R.id.course_view_mentor_email);
        textViewCourseStatus = findViewById(R.id.course_view_course_status);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getCourse(courseId).observe(this, new Observer<Course>() {
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
                Intent intent = new Intent(ViewCourseActivity.this, AddEditCourseActivity.class);
                Course course = createCourse();
                intent.putExtra(AddEditCourseActivity.EXTRA_ID, course.getCourseId());
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddEditCourseActivity.EXTRA_START_DATE, course.getCourseStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END_DATE, course.getCourseEndDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_NAME, course.getCourseMentorName());
                intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());
                intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_PHONE, course.getCourseMentorPhone());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, course.getCourseStatus());
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, course.getTermId());
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title;
        long startDate;
        long endDate;
        String mentorName;
        String mentorEmail;
        String mentorPhone;
        String courseStatus;
        int termId;

        if (resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
            title = data.getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
            startDate = data.getLongExtra(AddEditCourseActivity.EXTRA_START_DATE, 0);
            endDate = data.getLongExtra(AddEditCourseActivity.EXTRA_END_DATE, 0);
            mentorName = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR_NAME);
            mentorEmail = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR_EMAIL);
            mentorPhone = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR_PHONE);
            courseStatus = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS);
            termId = data.getIntExtra(AddEditCourseActivity.EXTRA_TERM_ID, -1);

            Course course = new Course(title, startDate, endDate, courseStatus, mentorName, mentorPhone, mentorEmail);
            course.setTermId(termId);

            if (id == -1) {
                Toast.makeText(this, "Course cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            course.setCourseId(id);
            courseViewModel.update(course);
            Toast.makeText(this, "Course Updated : ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private Course createCourse() {
        long startDateLong;
        long endDateLong;
        String title = textViewTitle.getText().toString();
        String mentorName = textViewMentorName.getText().toString();
        String mentorPhone = textViewMentorPhone.getText().toString();
        String mentorEmail = textViewMentorEmail.getText().toString();
        String courseStatus = textViewCourseStatus.getText().toString();

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormat.parse(textViewStartDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startDateLong = calendar.getTimeInMillis();
        try {
            calendar.setTime(dateFormat.parse(textViewEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDateLong = calendar.getTimeInMillis();


        Course course = new Course(title, startDateLong, endDateLong, courseStatus, mentorName, mentorPhone, mentorEmail);
        course.setCourseId(courseId);
        course.setTermId(termId);
        return course;
    }
}
