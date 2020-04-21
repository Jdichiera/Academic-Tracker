package com.example.academictracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private int courseId;
    private long assessmentDueDate;
    private boolean isPerformance;

    private String assessmentTitle;

    public Assessment(String assessmentTitle, long assessmentDueDate, boolean isPerformance) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.isPerformance = isPerformance;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public boolean getIsPerformance() {
        return this.isPerformance;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public long getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(long assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }
}
