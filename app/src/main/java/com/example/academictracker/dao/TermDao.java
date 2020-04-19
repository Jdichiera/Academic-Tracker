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
    long insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM terms_table")
    void deleteAllTerms();

    @Query("SELECT * FROM terms_table")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM terms_table WHERE id = :termId")
    LiveData<Term> getTerm(int termId);

    @Query("SELECT * FROM terms_table WHERE startDate <= :currentDate AND endDate >= :currentDate ORDER BY startDate ASC LIMIT 1")
    LiveData<Term> getCurrentTerm(long currentDate);

    @Query("SELECT id FROM terms_table WHERE startDate <= :currentDate AND endDate >= :currentDate ORDER BY startDate ASC LIMIT 1")
    LiveData<Integer> getCurrentTermId(long currentDate);

    @Query("delete from sqlite_sequence where name='terms_table'")
    void resetKeys();
}
