package com.example.academictracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.academictracker.entity.Assessment;
import com.example.academictracker.entity.Term;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("DELETE FROM assessments_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments_table WHERE courseId = :courseId")
    LiveData<List<Assessment>> getAllAssessmentsForCourse(int courseId);

    @Query("SELECT * FROM assessments_table WHERE assessmentId = :assessmentId")
    LiveData<Assessment> getAssessment(int assessmentId);

    @Query("delete from sqlite_sequence where name='assessments_table'")
    void resetKeys();
}
