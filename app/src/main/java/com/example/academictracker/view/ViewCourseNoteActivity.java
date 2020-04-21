package com.example.academictracker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.factories.CourseNoteViewModelFactory;
import com.example.academictracker.viewmodel.CourseNoteViewModel;

public class ViewCourseNoteActivity extends AppCompatActivity {
    public static final int ADD_COURSE_NOTE_REQUEST = 1;
    public static final int EDIT_COURSE_NOTE_REQUEST = 2;
    int courseId;
    private TextView textViewCourseNoteTitle;
    private TextView textViewCourseNoteContent;
    private CourseNoteViewModel courseNoteViewModel;
    private CourseNote courseNote;
    private Button buttonAddEditNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_note);

        Button buttonShareNote = findViewById(R.id.button_view_course_note_share_note);
        Button buttonDeleteNote = findViewById(R.id.button_view_course_note_delete_note);
        buttonAddEditNote = findViewById(R.id.button_view_course_note_add_edit_note);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(AddEditCourseNoteActivity.EXTRA_COURSE_ID, -1);
        textViewCourseNoteTitle = findViewById(R.id.course_note_view_note_title);
        textViewCourseNoteContent = findViewById(R.id.course_note_view_note_content);
        textViewCourseNoteContent.setMovementMethod(new ScrollingMovementMethod());

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
        if (buttonAddEditNote.getText().toString().equals("Edit Note")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, textViewCourseNoteContent.getText().toString());
            startActivity(Intent.createChooser(intent, "Share course note with"));
        } else {
            Toast.makeText(this, "Please add a course note to share.", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void addNote() {
        Intent intent = new Intent(ViewCourseNoteActivity.this, AddEditCourseNoteActivity.class);
        intent.setData(null);
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_COURSE_ID, courseId);
        startActivityForResult(intent, ADD_COURSE_NOTE_REQUEST);
    }

    private void editNote() {
        Toast.makeText(this, "Edit Note", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ViewCourseNoteActivity.this, AddEditCourseNoteActivity.class);
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_ID, courseNote.getCourseNoteId());
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_COURSE_ID, courseNote.getCourseId());
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_TITLE, courseNote.getCourseNoteTitle());
        intent.putExtra(AddEditCourseNoteActivity.EXTRA_CONTENT, courseNote.getCourseNoteContent());
        startActivityForResult(intent, EDIT_COURSE_NOTE_REQUEST);
    }

    private void deleteNote() {
        if (courseNote != null) {
            courseNoteViewModel.deleteCourseNote(courseNote);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int courseId;
        int courseNoteId;
        String courseNoteTitle;
        String courseNoteContent;

        if (resultCode == RESULT_OK) {
            courseId = data.getIntExtra(AddEditCourseNoteActivity.EXTRA_COURSE_ID, -1);
            courseNoteTitle = data.getStringExtra(AddEditCourseNoteActivity.EXTRA_TITLE);
            courseNoteContent = data.getStringExtra(AddEditCourseNoteActivity.EXTRA_CONTENT);
            CourseNote courseNote = new CourseNote(courseNoteTitle, courseNoteContent, courseId);

            if (requestCode == ADD_COURSE_NOTE_REQUEST) {
                courseNoteViewModel.insert(courseNote);
            } else if (requestCode == EDIT_COURSE_NOTE_REQUEST) {
                courseNoteId = data.getIntExtra(AddEditCourseNoteActivity.EXTRA_ID, -1);

                if (courseNoteId == -1) {
                    Toast.makeText(this, "Course cannot be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                courseNote.setCourseNoteId(courseNoteId);
                courseNote.setCourseNoteId(courseNoteId);
                courseNoteViewModel.update(courseNote);
            } else {
                Toast.makeText(this, "Course note not saved", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
