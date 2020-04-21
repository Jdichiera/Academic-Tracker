package com.example.academictracker.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.academictracker.entity.Assessment;
import com.example.academictracker.repository.AssessmentRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepository repository;
    private LiveData<List<Assessment>> allAssessmentsForCourse;

    public AssessmentViewModel(Application application, int courseId) {
        super(application);
        repository = new AssessmentRepository(application, courseId);
        allAssessmentsForCourse = repository.getAllAssessmentsForCourse();
    }

    public AssessmentViewModel(Application application) {
        super(application);
        repository = new AssessmentRepository(application);
    }

    public void resetKeys() {
        repository.resetKeys();
    }

    public void insert(Assessment assessment) {
        repository.insert(assessment);
    }

    public void update(Assessment assessment) {
        repository.update(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }

    public void deleteAllAssessments() {
        repository.deleteAllAssessments();
    }

    public LiveData<Assessment> getAssessment(int assessmentId) {
        return repository.getAssessment(assessmentId);
    }

    public LiveData<List<Assessment>> getAllAssessmentsForCourse() {
        return allAssessmentsForCourse;
    }
}
