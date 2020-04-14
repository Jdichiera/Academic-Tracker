package com.example.academictracker.factories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.academictracker.viewmodel.AssessmentViewModel;

public class AssessmentViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int courseId;

    public AssessmentViewModelFactory(Application application, int courseId) {
        this.application = application;
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AssessmentViewModel(this.application, this.courseId);
    }
}
