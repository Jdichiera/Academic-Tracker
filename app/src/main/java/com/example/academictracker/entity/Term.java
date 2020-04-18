package com.example.academictracker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms_table")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Long startDate;
    private Long endDate;
    @Ignore
    @ColumnInfo(name = "Count")
    private int termCourseCount;

    public Term(String title, Long startDate, Long endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTermCourseCount(int courseCount) {
        this.termCourseCount = courseCount;
    }

    public int getTermCourseCount() {
        return this.termCourseCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }
}
