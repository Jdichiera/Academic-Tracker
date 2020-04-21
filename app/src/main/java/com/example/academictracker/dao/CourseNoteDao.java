package com.example.academictracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.academictracker.entity.CourseNote;

@Dao
public interface CourseNoteDao {
    @Insert
    void insert(CourseNote note);

    @Update
    void update(CourseNote note);

    @Delete
    void deleteCourseNote(CourseNote note);

    @Query("DELETE FROM course_notes_table")
    void deleteAllCourseNotes();

    @Query("SELECT * FROM course_notes_table WHERE courseId = :courseId")
    LiveData<CourseNote> getCourseNoteByCourse(int courseId);

    @Query("SELECT * FROM course_notes_table WHERE courseId = :courseNoteId")
    LiveData<CourseNote> getCourseNote(int courseNoteId);

    @Query("delete from sqlite_sequence where name='course_notes_table'")
    void resetKeys();
}
