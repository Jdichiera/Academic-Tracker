package com.example.academictracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.academictracker.dao.CourseDao;
import com.example.academictracker.dao.CourseNoteDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.CourseNote;

public class CourseNoteRepository {
    private CourseNoteDao courseNoteDao;
    private LiveData<CourseNote> courseNote;

    public CourseNoteRepository(Application application) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        courseNoteDao = database.courseNoteDao();
    }

    public CourseNoteRepository(Application application, int courseId) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        courseNoteDao = database.courseNoteDao();
        courseNote = courseNoteDao.getCourseNoteByCourse(courseId);
    }

    public void insert(CourseNote note) {
        new CourseNoteRepository.InsertCourseNoteAsyncTask(courseNoteDao).execute(note);
    }

    public void update(CourseNote note) {
        new CourseNoteRepository.UpdateCourseNoteAsyncTask(courseNoteDao).execute(note);
    }

    public void deleteCourseNote(CourseNote note) {
        new CourseNoteRepository.DeleteCourseNoteAsyncTask(courseNoteDao).execute(note);
    }

    public void deleteAllCourseNotes() {
        new CourseNoteRepository.DeleteAllCourseNotesAsyncTask(courseNoteDao).execute();
    }

    public void resetKeys() {
        new CourseNoteRepository.ResetKeysAsyncTask(courseNoteDao).execute();
    }

    public LiveData<CourseNote> getCourseNote(int id) {
        return courseNoteDao.getCourseNote(id);
    }

    public LiveData<CourseNote> getCourseNote() {
        return courseNote;
    }

    private static class InsertCourseNoteAsyncTask extends AsyncTask<CourseNote, Void, Void> {
        private CourseNoteDao courseNoteDao;

        private InsertCourseNoteAsyncTask(CourseNoteDao courseNoteDao) {
            this.courseNoteDao = courseNoteDao;
        }

        @Override
        protected Void doInBackground(CourseNote... notes) {
            courseNoteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateCourseNoteAsyncTask extends AsyncTask<CourseNote, Void, Void> {
        private CourseNoteDao courseNoteDao;

        private UpdateCourseNoteAsyncTask(CourseNoteDao courseNoteDao) {
            this.courseNoteDao = courseNoteDao;
        }

        @Override
        protected Void doInBackground(CourseNote... notes) {
            courseNoteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteCourseNoteAsyncTask extends AsyncTask<CourseNote, Void, Void> {
        private CourseNoteDao courseNoteDao;

        private DeleteCourseNoteAsyncTask(CourseNoteDao courseNoteDao) {
            this.courseNoteDao = courseNoteDao;
        }

        @Override
        protected Void doInBackground(CourseNote... notes) {
            courseNoteDao.delteCourseNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllCourseNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseNoteDao courseNoteDao;

        private DeleteAllCourseNotesAsyncTask(CourseNoteDao courseNoteDao) {
            this.courseNoteDao = courseNoteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseNoteDao.deleteAllCourseNotes();
            return null;
        }
    }

    private static class ResetKeysAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseNoteDao courseNoteDao;

        private ResetKeysAsyncTask(CourseNoteDao courseNoteDao) {
            this.courseNoteDao = courseNoteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseNoteDao.resetKeys();
            return null;
        }
    }
}
