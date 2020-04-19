package com.example.academictracker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.application.AcademicTracker;
import com.example.academictracker.entity.Assessment;
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.factories.CourseNoteViewModelFactory;
import com.example.academictracker.utility.NotificationHelper;
import com.example.academictracker.utility.NotificationReceiver;
import com.example.academictracker.viewmodel.AssessmentViewModel;
import com.example.academictracker.viewmodel.CourseNoteViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewAssessmentActivity extends AppCompatActivity {
    private int courseId;
    private int assessmentId;
    private TextView textViewTitle;
    private TextView textViewDueDate;
    private TextView textViewCourseNote;
    private AssessmentViewModel assessmentViewModel;
    private CourseNoteViewModel courseNoteViewModel;
    public static final int EDIT_ASSESSMENT_REQUEST = 1;
    final Calendar calendar = Calendar.getInstance();
//    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);

        Button buttonSetNotification = findViewById(R.id.button_assessment_view_set_notfication);
        Button buttonAddEditAssessment = findViewById(R.id.button_assessment_view_edit_assessment);
        Button buttonDeleteAssessment = findViewById(R.id.button_assessment_view_delete_assessment);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);
        assessmentId = intent.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);
        textViewTitle = findViewById(R.id.assessment_view_title);
        textViewDueDate = findViewById(R.id.assessment_view_due_date);
        textViewCourseNote = findViewById(R.id.assessment_view_course_note);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessment(assessmentId).observe(this, new Observer<Assessment>() {
            @Override
            public void onChanged(Assessment assessment) {
                if (assessment != null) {
                    calendar.setTimeInMillis(assessment.getAssessmentDueDate());
                    textViewTitle.setText(assessment.getAssessmentTitle());
                    textViewDueDate.setText((AcademicTracker.dateFormat.format(calendar.getTime())));
                }
            }
        });

        courseNoteViewModel = ViewModelProviders.of(this, new CourseNoteViewModelFactory(this.getApplication(), courseId)).get(CourseNoteViewModel.class);
        courseNoteViewModel.getCourseNote(courseId).observe(this, new Observer<CourseNote>() {
            @Override
            public void onChanged(CourseNote courseNote) {
                if (courseNote != null) {
                    textViewCourseNote.setText(courseNote.getCourseNoteContent());
                }
            }
        });

        buttonSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotification();
                Toast.makeText(ViewAssessmentActivity.this, "Set Notification", Toast.LENGTH_SHORT).show();
            }
        });

        buttonAddEditAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAssessment();
            }
        });

        buttonDeleteAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssessment();
            }
        });
    }

    private void deleteAssessment() {
        cancelNotification();
        Assessment assessment = createAssessment();
        assessmentViewModel.deleteAssessment(assessment);
        finish();
    }

    private void editAssessment() {
        Intent intent = new Intent(ViewAssessmentActivity.this, AddEditAssessmentActivity.class);
        Assessment assessment = createAssessment();
        intent.putExtra(AddEditAssessmentActivity.EXTRA_ID, assessment.getAssessmentId());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_TITLE, assessment.getAssessmentTitle());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_DUE_DATE, assessment.getAssessmentDueDate());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, assessment.getCourseId());
        startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
    }

    private void setNotification() {
        int notificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.ASSESSMENT_DUE, assessmentId);
        String assessmentName = textViewTitle.getText().toString();
        String assessmentDueDate = textViewDueDate.getText().toString();
        String notificationTitle = assessmentName + " is due today.";

        calendar.setTime(getAssessmentDueDate());

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE, notificationTitle);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelNotification() {
        int notificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.ASSESSMENT_DUE, assessmentId);

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, notificationId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }

    private Assessment createAssessment() {
        long dueDate;
        String title = textViewTitle.getText().toString();

        Calendar calendar = Calendar.getInstance();

//        try {
//            calendar.setTime(dateFormat.parse(textViewDueDate.getText().toString()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        calendar.setTime(getAssessmentDueDate());
        dueDate = calendar.getTimeInMillis();

        Assessment assessment = new Assessment(title, dueDate);
        assessment.setAssessmentId(assessmentId);
        assessment.setCourseId(courseId);
        return assessment;
    }

    public Date getAssessmentDueDate()  {
        Date endDate = new Date();
        try {
            endDate = AcademicTracker.dateFormat.parse(textViewDueDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title;
        long dueDate;
        int courseId;

        if (resultCode == RESULT_OK) {
            int assessmentId = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);
            if (assessmentId == -1) {
                Toast.makeText(this, "Assessment cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            dueDate = data.getLongExtra(AddEditAssessmentActivity.EXTRA_DUE_DATE, 0);
            courseId = data.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);

            Assessment assessment = new Assessment(title, dueDate);
            assessment.setCourseId(courseId);
            assessment.setAssessmentId(assessmentId);
            assessment.setCourseId(courseId);
            assessmentViewModel.update(assessment);
            Toast.makeText(this, "Assessment Updated : ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }
    }
}

