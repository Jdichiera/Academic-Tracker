package com.example.academictracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_notes_table")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int courseNoteId;
    private int courseId;
    private String courseNoteTitle;
    private String courseNoteContent;

    public CourseNote(String courseNoteTitle, String courseNoteContent, int courseId) {
        this.courseId = courseId;
        this.courseNoteTitle = courseNoteTitle;
        this.courseNoteContent = courseNoteContent;
    }

    public int getCourseNoteId() {
        return courseNoteId;
    }

    public void setCourseNoteId(int courseNoteId) {
        this.courseNoteId = courseNoteId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseNoteTitle() {
        return courseNoteTitle;
    }

    public void setCourseNoteTitle(String courseNoteTitle) {
        this.courseNoteTitle = courseNoteTitle;
    }

    public String getCourseNoteContent() {
        return courseNoteContent;
    }

    public void setCourseNoteContent(String courseNoteContent) {
        this.courseNoteContent = courseNoteContent;
    }
}
