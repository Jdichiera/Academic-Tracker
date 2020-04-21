package com.example.academictracker.factories;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.academictracker.viewmodel.CourseViewModel;

public class CourseViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int termId;

    public CourseViewModelFactory(Application application, int termId) {
        this.application = application;
        this.termId = termId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CourseViewModel(this.application, this.termId);
    }
}
