package com.example.academictracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.academictracker.dao.AssessmentDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Assessment;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentRepository(Application application) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();
    }

    public void insert(Assessment assessment) {
        new AssessmentRepository.InsertAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void update(Assessment assessment) {
        new AssessmentRepository.UpdateAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        new AssessmentRepository.DeleteAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public LiveData<Assessment> getAssessment(int id) {
        return assessmentDao.getAssessment(id);
    }

    public void deleteAllAssessments() {
        new AssessmentRepository.DeleteAllAssessmentsAsyncTask(assessmentDao).execute();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    private static class InsertAssessmentAsyncTask extends android.os.AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.insert(assessments[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends android.os.AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.update(assessments[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends android.os.AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.deleteAssessment(assessments[0]);
            return null;
        }
    }

    private static class DeleteAllAssessmentsAsyncTask extends android.os.AsyncTask<Void, Void, Void> {
        private AssessmentDao assessmentDao;

        private DeleteAllAssessmentsAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            assessmentDao.deleteAllAssessments();
            return null;
        }
    }
}
