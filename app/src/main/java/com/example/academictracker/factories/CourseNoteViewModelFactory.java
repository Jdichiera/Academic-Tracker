package com.example.academictracker.factories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.academictracker.viewmodel.CourseNoteViewModel;
import com.example.academictracker.viewmodel.CourseViewModel;

public class CourseNoteViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int courseId;

    public CourseNoteViewModelFactory(Application application, int courseId) {
        this.application = application;
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CourseNoteViewModel(this.application, this.courseId);
    }
}
