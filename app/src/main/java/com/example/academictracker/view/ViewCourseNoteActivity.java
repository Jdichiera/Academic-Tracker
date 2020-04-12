package com.example.academictracker.view;

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
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.factories.CourseNoteViewModelFactory;
import com.example.academictracker.viewmodel.CourseNoteViewModel;

public class ViewCourseNoteActivity extends AppCompatActivity {
    private TextView textViewCourseNoteTitle;
    private TextView textViewCourseNoteContent;
    private CourseNoteViewModel courseNoteViewModel;
    private CourseNote courseNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int courseId;
        String courseNoteTitle;
        final String courseNoteText;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_note);

        Button buttonShareNote = findViewById(R.id.button_view_course_note_share_note);
        final Button buttonAddEditNote = findViewById(R.id.button_view_course_note_add_edit_note);
        Button buttonDeleteNote = findViewById(R.id.button_view_course_note_delete_note);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
        textViewCourseNoteTitle = findViewById(R.id.course_note_view_note_title);
        textViewCourseNoteContent = findViewById(R.id.course_note_view_note_text);

        courseNoteViewModel = ViewModelProviders.of(this, new CourseNoteViewModelFactory(this.getApplication(), courseId)).get(CourseNoteViewModel.class);
        courseNoteViewModel.getCourseNote().observe(this, new Observer<CourseNote>() {
            @Override
            public void onChanged(CourseNote observedCourseNote) {
                if (observedCourseNote != null) {
                    courseNote = observedCourseNote;
                    textViewCourseNoteTitle.setText(observedCourseNote.getCourseNoteTitle());
                    textViewCourseNoteContent.setText(observedCourseNote.getCourseNoteContent());
                    buttonAddEditNote.setText("Edit Note");
                } else {
                    textViewCourseNoteContent.setText("No note has been entered for this course. Click 'Add Note' to add a note for this course.");
                    buttonAddEditNote.setText("Add Note");
                }
            }
        });

        buttonShareNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();
            }
        });

        buttonAddEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseNote != null) {
                    editNote();
                } else {
                    addNote();
                }
            }
        });

        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });
    }

    private void shareNote() {
        Toast.makeText(this, "Share Note", Toast.LENGTH_SHORT).show();
    }

    private void addNote() {
        Toast.makeText(this, "Add Note", Toast.LENGTH_SHORT).show();
    }

    private void editNote() {
        Toast.makeText(this, "Edit Note", Toast.LENGTH_SHORT).show();
    }

    private void deleteNote() {
        Toast.makeText(this, "Delete Note", Toast.LENGTH_SHORT).show();
    }
}
