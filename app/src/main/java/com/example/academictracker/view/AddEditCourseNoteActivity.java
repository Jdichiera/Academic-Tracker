package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academictracker.R;

public class AddEditCourseNoteActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "com.example.academictracker.view.EXTRA_COURSE_ID";
    public static final String EXTRA_ID = "com.example.academictracker.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.academictracker.view.EXTRA_CONTENT";

    int courseId;
    int courseNoteId;
    EditText editTextCourseNoteTitle;
    EditText editTextCourseNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course_note);

        editTextCourseNoteTitle = findViewById(R.id.course_note_add_title);
        editTextCourseNoteContent = findViewById(R.id.course_note_add_content);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Course Note");
            courseNoteId = intent.getIntExtra(EXTRA_ID, -1);
            courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);
            editTextCourseNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextCourseNoteContent.setText(intent.getStringExtra(EXTRA_CONTENT));
        } else {
            setTitle("Add Course Note");
        }
    }

    public void saveCourseNote(View view) {
        String courseNoteTitle = editTextCourseNoteTitle.getText().toString();
        String courseNoteContent = editTextCourseNoteContent.getText().toString();

        if (courseNoteTitle.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_ID, courseId);
        data.putExtra(EXTRA_TITLE, courseNoteTitle);
        data.putExtra(EXTRA_CONTENT, courseNoteContent);

        if (courseNoteId != -1) {
            data.putExtra(EXTRA_ID, courseNoteId);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
