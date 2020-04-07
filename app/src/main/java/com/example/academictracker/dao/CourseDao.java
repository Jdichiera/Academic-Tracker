package com.example.academictracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.academictracker.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table WHERE termId = :termId")
    LiveData<List<Course>> getAllCoursesForTerm(int termId);

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    LiveData<Course> getCourse(int courseId);
}
