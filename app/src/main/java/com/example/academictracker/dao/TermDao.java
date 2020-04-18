package com.example.academictracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM terms_table")
    void deleteAllTerms();

    @Query("SELECT * FROM terms_table")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM terms_table WHERE id = :termId")
//    @Query("SELECT id, title, startDate, endDate, Count(courses_table.courseTitle) FROM terms_table LEFT JOIN courses_table ON terms_table.id = courses_table.termId WHERE terms_table.id = :termId")
    LiveData<Term> getTerm(int termId);
}
