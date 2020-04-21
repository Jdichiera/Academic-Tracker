package com.example.academictracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.application.AcademicTracker;
import com.example.academictracker.entity.Assessment;
import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.AssessmentViewModel;
import com.example.academictracker.viewmodel.CourseNoteViewModel;
import com.example.academictracker.viewmodel.CourseViewModel;
import com.example.academictracker.viewmodel.TermViewModel;
import java.text.ParseException;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonViewAllTerms = findViewById(R.id.button_view_all_terms);
        Button buttonViewCurrentTerms = findViewById(R.id.button_view_current_term);

        buttonViewAllTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TermListActivity.class);
                startActivity(intent);
            }
        });

        buttonViewCurrentTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewTermActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_sample_data:
                Toast.makeText(this, "Add Sample Data", Toast.LENGTH_SHORT).show();
                addData();
                return true;
            case R.id.delete_all_data:
                Toast.makeText(this, "Delete All Data", Toast.LENGTH_SHORT).show();
                deleteAllData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllData() {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(getApplicationContext()
                .getDatabasePath("terms_database"), null);
        database.execSQL("DELETE FROM course_notes_table");
        database.execSQL("DELETE FROM assessments_table");
        database.execSQL("DELETE FROM courses_table");
        database.execSQL("DELETE FROM terms_table");
        database.execSQL("delete from sqlite_sequence where name = 'course_notes_table'");
        database.execSQL("delete from sqlite_sequence where name = 'assessments_table'");
        database.execSQL("delete from sqlite_sequence where name = 'courses_table'");
        database.execSQL("delete from sqlite_sequence where name = 'terms_table'");
    }

    private void addData() {
        addTermData();
        addCourseData();
        addCourseNoteData();
        addAssessmentData();
    }

    private void addTermData() {
        TermViewModel termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        long startDate;
        long endDate;

        Calendar calendar = Calendar.getInstance();
        startDate = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, 2);
        endDate = calendar.getTimeInMillis();
        Term currentTerm = new Term("Current Term (w/ courses)", startDate, endDate);

        startDate = getDateAsLong("01/01/2019");
        endDate = getDateAsLong("02/01/2019");
        Term pastTerm = new Term("Past Term (no courses)", startDate, endDate);

        startDate = getDateAsLong("08/01/2125");
        endDate = getDateAsLong("09/01/2125");
        Term futureTerm = new Term("Future Term (w/ courses)", startDate, endDate);

        termViewModel.insert(pastTerm);
        termViewModel.insert(currentTerm);
        termViewModel.insert(futureTerm);
    }

    private void addCourseData() {
        CourseViewModel courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        String mentorName = "Jim Mentor";
        String mentorPhone = "987-654-3121";
        String mentorEmail = "mentor@email.com";

        Course course1 = new Course(
                "Course 1 (Current Course)",
                getDateAsLong("04/21/2020"),
                getDateAsLong("06/31/2020"),
                Course.CourseStatus.PLAN_TO_TAKE.label,
                mentorName,
                mentorPhone,
                mentorEmail
        );
        course1.setTermId(2);

        Course course2 = new Course(
                "Course 2",
                getDateAsLong("07/01/2021"),
                getDateAsLong("08/01/2021"),
                Course.CourseStatus.PLAN_TO_TAKE.label,
                mentorName,
                mentorPhone,
                mentorEmail
        );
        course2.setTermId(3);

        courseViewModel.insert(course1);
        courseViewModel.insert(course2);
    }

    private void addCourseNoteData() {
        CourseNoteViewModel courseNoteViewModel = ViewModelProviders.of(this).get(CourseNoteViewModel.class);
        CourseNote courseNote1 = new CourseNote("Course 1 Course Note", "This is the course note for course 1", 1);
        CourseNote courseNote2 = new CourseNote("Course 2 Course Note", "This is the course note for course 2", 2);

        courseNoteViewModel.insert(courseNote1);
        courseNoteViewModel.insert(courseNote2);
    }

    private void addAssessmentData() {
        AssessmentViewModel assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        Assessment performanceAssessment = new Assessment("Performance Assessment for Course 1", getDateAsLong("04/27/2020"), true);
        Assessment objectiveAssessment = new Assessment("Objective Assessment for Course 1", getDateAsLong("04/27/2020"), false);
        Assessment performanceAssessment2 = new Assessment("Performance Assessment for Course 12", getDateAsLong("04/27/2020"), true);
        Assessment objectiveAssessment2 = new Assessment("Objective Assessment for Course 12", getDateAsLong("04/27/2020"), false);

        performanceAssessment.setCourseId(1);
        objectiveAssessment.setCourseId(1);
        performanceAssessment2.setCourseId(2);
        objectiveAssessment2.setCourseId(2);

        assessmentViewModel.insert(performanceAssessment);
        assessmentViewModel.insert(objectiveAssessment);
        assessmentViewModel.insert(performanceAssessment2);
        assessmentViewModel.insert(objectiveAssessment2);
    }

    private long getDateAsLong(String time) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(AcademicTracker.dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar.getTimeInMillis();
    }

}
