package com.example.academictracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

enum CourseStatus {
    STARTED, IN_PROGRESS, COMPLETED, DROPPED
}

@Entity(tableName = "course_table")
public class Course {


    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseTitle;
    private long courseStartDate;
    private long courseEndDate;
    private CourseStatus courseStatus;
    private String courseMentorName;
    private String courseMentorNumber;
    private String courseMentorEmail;

    public Course(String courseTitle, long courseStartDate, long courseEndDate, CourseStatus courseStatus, String courseMentorName, String courseMentorNumber, String courseMentorEmail) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentorName = courseMentorName;
        this.courseMentorNumber = courseMentorNumber;
        this.courseMentorEmail = courseMentorEmail;
    }

    public void setCourseId(int id) {
        this.courseId = id;
    }

    public void setTermId(int id) {
        this.termId = id;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public int getTermId() {
        return this.termId;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public long getCourseStartDate() {
        return this.courseStartDate;
    }

    public long getCourseEndDate() {
        return this.courseEndDate;
    }

    public CourseStatus getCourseStatus() {
        return this.courseStatus;
    }

    public String getCourseMentorName() {
        return this.courseMentorName;
    }

    public String getCourseMentorNumber() {
        return this.courseMentorNumber;
    }

    public String getCourseMentorEmail() {
        return this.courseMentorEmail;
    }
}
