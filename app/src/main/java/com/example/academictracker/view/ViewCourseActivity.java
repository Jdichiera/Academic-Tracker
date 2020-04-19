package com.example.academictracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.application.AcademicTracker;
import com.example.academictracker.entity.Course;
import com.example.academictracker.utility.NotificationHelper;
import com.example.academictracker.utility.NotificationReceiver;
import com.example.academictracker.viewmodel.CourseViewModel;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
    final Calendar calendar = Calendar.getInstance();


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
                    textViewStartDate.setText(AcademicTracker.dateFormat.format(calendar.getTime()));
                    calendar.setTimeInMillis(course.getCourseEndDate());
                    textViewEndDate.setText(AcademicTracker.dateFormat.format(calendar.getTime()));
                    textViewMentorName.setText(course.getCourseMentorName());
                    textViewMentorPhone.setText(course.getCourseMentorPhone());
                    textViewMentorEmail.setText(course.getCourseMentorEmail());
                    textViewCourseStatus.setText(course.getCourseStatus());
                }
            }
        });

        buttonSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotifications();
            }
        });

        buttonViewCourseNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCourseNote();
            }
        });

        buttonEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCourse();
            }
        });

        buttonViewCourseAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCourseAssessments();
            }
        });

        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_course_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String courseStatus = textViewCourseStatus.getText().toString();
        int statusPosition = Course.CourseStatus.valueOfLabel(courseStatus).ordinal();
        switch (statusPosition) {
            case 0: // Not Started
                menu.findItem(R.id.menu_set_course_status_in_progress).setVisible(true);
                menu.findItem(R.id.menu_set_course_status_complete).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_drop).setVisible(true);
                break;
            case 1: // In Progress
                menu.findItem(R.id.menu_set_course_status_in_progress).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_complete).setVisible(true);
                menu.findItem(R.id.menu_set_course_status_drop).setVisible(true);
                break;
            case 2: // Completed
                menu.findItem(R.id.menu_set_course_status_in_progress).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_complete).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_drop).setVisible(true);
                break;
            case 3: // Dropped
                menu.findItem(R.id.menu_set_course_status_in_progress).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_complete).setVisible(false);
                menu.findItem(R.id.menu_set_course_status_drop).setVisible(false);
                break;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_course:
                editCourse();
                return true;
            case R.id.menu_delete_course:
                deleteCourse();
                return true;
            case R.id.menu_set_course_reset_status:
                setCourseStatus(Course.CourseStatus.NOT_STARTED.label);
                return true;
            case R.id.menu_set_course_status_in_progress:
                setCourseStatus(Course.CourseStatus.IN_PROGRESS.label);
                return true;
            case R.id.menu_set_course_status_complete:
                setCourseStatus(Course.CourseStatus.COMPLETED.label);
                return true;
            case R.id.menu_set_course_status_drop:
                setCourseStatus(Course.CourseStatus.DROPPED.label);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNotifications() {
        setCourseBeginNotification();
        setCourseEndNotification();
        Toast.makeText(this, "Notifications set for course.", Toast.LENGTH_SHORT).show();
    }

    private void editCourse() {
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

    private void viewCourseNote() {
        Intent intent = new Intent(ViewCourseActivity.this, ViewCourseNoteActivity.class);
        Course course = createCourse();
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_COURSE_ID, course.getCourseId());
        startActivity(intent);
    }

    private void deleteCourse() {
        cancelNotifications();
        Course course = createCourse();
        course.setCourseId(courseId);
        courseViewModel.deleteCourse(course);
        finish();
    }

    private void viewCourseAssessments() {
        Intent intent = new Intent(ViewCourseActivity.this, AssessmentListActivity.class);
        intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, courseId);
        startActivity(intent);
    }

    private void setCourseStatus(String newStatus) {
        Course course = createCourse();
        course.setCourseStatus(newStatus);
        courseViewModel.update(course);
    }

    private Course createCourse() {
        long startDateLong;
        long endDateLong;
        String title = textViewTitle.getText().toString();
        String mentorName = textViewMentorName.getText().toString();
        String mentorPhone = textViewMentorPhone.getText().toString();
        String mentorEmail = textViewMentorEmail.getText().toString();
        String courseStatus = textViewCourseStatus.getText().toString();

        calendar.setTime(getCourseStartDate());
        startDateLong = calendar.getTimeInMillis();
        calendar.setTime(getCourseEndDate());
        endDateLong = calendar.getTimeInMillis();

        Course course = new Course(title, startDateLong, endDateLong, courseStatus, mentorName, mentorPhone, mentorEmail);
        course.setCourseId(courseId);
        course.setTermId(termId);
        return course;
    }

    protected void setCourseBeginNotification() {
        int notificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.COURSE_BEGIN, courseId);
        String courseName = textViewTitle.getText().toString();
        String courseStartDate = textViewStartDate.getText().toString();
        String notificationTitle = courseName + " is starting today.";
        calendar.setTime(getCourseStartDate());

        Intent courseBeginIntent = new Intent(this, NotificationReceiver.class);
        courseBeginIntent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE, notificationTitle);
        courseBeginIntent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, courseBeginIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void setCourseEndNotification() {
        int notificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.COURSE_END, courseId);
        String courseName = textViewTitle.getText().toString();
        String courseEndDate = textViewEndDate.getText().toString();
        String notificationTitle = courseName + " is ending today.";

        calendar.setTime(getCourseEndDate());

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE, notificationTitle);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelNotifications() {
        int beginNotificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.COURSE_BEGIN, courseId);
        int endNotificationId = NotificationHelper.generateNotificationId(NotificationHelper.NotificationCategory.COURSE_END, courseId);

        Intent beginIntent = new Intent(this, NotificationReceiver.class);
        beginIntent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, beginNotificationId);
        PendingIntent beginPendingIntent = PendingIntent.getBroadcast(this, 1, beginIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent endIntent = new Intent(this, NotificationReceiver.class);
        endIntent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, endNotificationId);
        PendingIntent endPendingIntent = PendingIntent.getBroadcast(this, 2, endIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(beginPendingIntent);
        alarm.cancel(endPendingIntent);
    }

    public Date getCourseStartDate() {
        Date startDate = new Date();
        try {
            startDate = AcademicTracker.dateFormat.parse(textViewStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public Date getCourseEndDate()  {
        Date endDate = new Date();
        try {
            endDate = AcademicTracker.dateFormat.parse(textViewEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
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
}
