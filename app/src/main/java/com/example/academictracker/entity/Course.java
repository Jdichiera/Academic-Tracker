package com.example.academictracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "courses_table")
public class Course {
    public enum CourseStatus {
        NOT_STARTED , IN_PROGRESS, COMPLETED, DROPPED
    }

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseTitle;
    private long courseStartDate;
    private long courseEndDate;
    private String courseStatus;
    private String courseMentorName;
    private String courseMentorPhone;
    private String courseMentorEmail;

    public Course(String courseTitle, long courseStartDate, long courseEndDate, String courseStatus, String courseMentorName, String courseMentorPhone, String courseMentorEmail) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentorName = courseMentorName;
        this.courseMentorPhone = courseMentorPhone;
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

    public String getCourseStatus() {
        return this.courseStatus;
    }

    public String getCourseMentorName() {
        return this.courseMentorName;
    }

    public String getCourseMentorPhone() {
        return this.courseMentorPhone;
    }

    public String getCourseMentorEmail() {
        return this.courseMentorEmail;
    }
}