package com.example.academictracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.academictracker.dao.CourseDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Course;

import java.util.List;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCoursesForTerm;

    public CourseRepository(Application application, int termId) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCoursesForTerm = courseDao.getAllCoursesForTerm(termId);
    }

    public CourseRepository(Application application) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        courseDao = database.courseDao();
    }

    public void insert(Course course) {
        new InsertCourseAsyncTask(courseDao).execute(course);
    }

    public void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void deleteCourse(Course course) {
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public LiveData<Course> getCourse(int id) {
        return courseDao.getCourse(id);
    }

    public void setCourseStatus(int id, String status) {
        courseDao.setCourseStatus(id, status);
    }
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(courseDao).execute();
    }

    public LiveData<List<Course>> getAllCoursesForTerm() {
        return allCoursesForTerm;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.deleteCourse(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseDao courseDao;

        private DeleteAllCoursesAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAllCourses();
            return null;
        }
    }
}
