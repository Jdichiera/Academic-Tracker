package com.example.academictracker.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.repository.CourseNoteRepository;

public class CourseNoteViewModel extends AndroidViewModel {
    private CourseNoteRepository repository;
    private LiveData<CourseNote> courseNote;

    public CourseNoteViewModel(Application application) {
        super(application);
        repository = new CourseNoteRepository(application);
    }

    public CourseNoteViewModel(Application application, int courseId) {
        super(application);
        repository = new CourseNoteRepository(application, courseId);
        courseNote = repository.getCourseNote();
    }

    public void insert(CourseNote note) {
        repository.insert(note);
    }

    public void resetKeys() {
        repository.resetKeys();
    }

    public void update(CourseNote note) {
        repository.update(note);
    }

    public void deleteCourseNote(CourseNote note) {
        repository.deleteCourseNote(note);
    }

    public void deleteAllCourseNotes() {
        repository.deleteAllCourseNotes();
    }

    public LiveData<CourseNote> getCourseNote(int id) {
        return repository.getCourseNote(id);
    }

    public LiveData<CourseNote> getCourseNote() {
        return courseNote;
    }
}
