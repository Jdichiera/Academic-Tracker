package com.example.academictracker.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.academictracker.entity.Course;
import com.example.academictracker.repository.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository repository;
    private Course course;
    private LiveData<List<Course>> allCoursesForTerm;

    public CourseViewModel(Application application, int termId) {
        super(application);
        repository = new CourseRepository(application, termId);
        allCoursesForTerm = repository.getAllCoursesForTerm();
    }

    public CourseViewModel(Application application) {
        super(application);
        repository = new CourseRepository(application);
    }

    public void resetKeys() {
        repository.resetKeys();
    }

    public void insert(Course course) {
        repository.insert(course);
    }

    public void update(Course course) {
        repository.update(course);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<Course> getCourse(int id) {
        return repository.getCourse(id);
    }

    public LiveData<List<Course>> getAllCoursesForTerm() {
        return allCoursesForTerm;
    }
}
