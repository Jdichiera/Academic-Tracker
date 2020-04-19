package com.example.academictracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.application.AcademicTracker;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.AssessmentViewModel;
import com.example.academictracker.viewmodel.CourseNoteViewModel;
import com.example.academictracker.viewmodel.CourseViewModel;
import com.example.academictracker.viewmodel.TermViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity {
    private final String PAST_TERM = "Past Term";
    private final String CURRENT_TERM = "Current Term";
    private final String FUTURE_TERM = "Future Term";
    private Map<String, Long> termIdMap = new HashMap<>();
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
            case R.id.create_alarm:
                Toast.makeText(this, "Create Alarm", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllData() {
        AssessmentViewModel assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.deleteAllAssessments();
        assessmentViewModel.resetKeys();

        CourseNoteViewModel courseNoteViewModel = ViewModelProviders.of(this).get(CourseNoteViewModel.class);
        courseNoteViewModel.deleteAllCourseNotes();
        courseNoteViewModel.resetKeys();

        CourseViewModel courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.deleteAllCourses();
        courseViewModel.resetKeys();

        TermViewModel termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.deleteAllTerms();
        termViewModel.resetKeys();
    }

    private void addData() {
        CourseViewModel courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        addTermData();
//        addCourseData();


//        Course course = new Course("Course 1 (Current Term)", );

    }

    private void addTermData() {
        TermViewModel termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        String pastTermStart = "01/01/2019";
        String pastTermEnd = "02/01/2019";
        String futureTermStart = "08/01/2125";
        String futureTermEnd = "09/01/2125";

        long startDate;
        long endDate;
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, 2);
        endDate = calendar.getTimeInMillis();
        Term currentTerm = new Term("Current Term (w/ courses)", startDate, endDate);

        setCalendarTime(calendar, pastTermStart);
        startDate = calendar.getTimeInMillis();
        setCalendarTime(calendar, pastTermEnd);
        endDate = calendar.getTimeInMillis();
        Term pastTerm = new Term("Past Term (no courses)", startDate, endDate);

        setCalendarTime(calendar, futureTermStart);
        startDate = calendar.getTimeInMillis();
        setCalendarTime(calendar, futureTermEnd);
        endDate = calendar.getTimeInMillis();
        Term futureTerm = new Term("Future Term (w/ courses)", startDate, endDate);


        termIdMap.put(PAST_TERM, termViewModel.insert(pastTerm));
        termIdMap.put(CURRENT_TERM, termViewModel.insert(currentTerm));
        termIdMap.put(FUTURE_TERM, termViewModel.insert(futureTerm));
    }
    private void setCalendarTime(Calendar calendar, String time) {
        try {
            calendar.setTime(AcademicTracker.dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
