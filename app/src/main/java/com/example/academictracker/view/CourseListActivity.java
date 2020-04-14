package com.example.academictracker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.adapters.CourseListAdapter;
import com.example.academictracker.entity.Course;
import com.example.academictracker.factories.CourseViewModelFactory;
import com.example.academictracker.viewmodel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseListActivity extends AppCompatActivity {
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Intent intent = getIntent();
        final int termId = intent.getIntExtra(AddEditCourseActivity.EXTRA_TERM_ID, -1);
        FloatingActionButton buttonAddCourse = findViewById(R.id.button_list_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseListActivity.this, AddEditCourseActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termId);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.course_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final CourseListAdapter adapter = new CourseListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CourseListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Course course) {
                editCourse(course);
            }

            @Override
            public void onViewClick(Course course) {
                viewCourse(course);
            }
        });

        courseViewModel = ViewModelProviders.of(this, new CourseViewModelFactory(this.getApplication(), termId)).get(CourseViewModel.class);
        courseViewModel.getAllCoursesForTerm().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });
    }

    private void editCourse(Course course) {
        Intent intent = new Intent(CourseListActivity.this, AddEditCourseActivity.class);
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

    private void viewCourse(Course course) {
        Intent intent = new Intent(CourseListActivity.this, ViewCourseActivity.class);
        intent.putExtra(AddEditCourseActivity.EXTRA_ID, course.getCourseId());
        intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, course.getTermId());
//        intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
//        intent.putExtra(AddEditCourseActivity.EXTRA_START_DATE, course.getCourseStartDate());
//        intent.putExtra(AddEditCourseActivity.EXTRA_END_DATE, course.getCourseEndDate());
//        intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_NAME, course.getCourseMentorName());
//        intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());
//        intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR_PHONE, course.getCourseMentorPhone());
//        intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, course.getCourseStatus());
        startActivity(intent);
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

            if (requestCode == ADD_COURSE_REQUEST) {
                courseViewModel.insert(course);
                Toast.makeText(this, "Course Added : " + title, Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_COURSE_REQUEST) {
                int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

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
    }
}

