package com.example.academictracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms_table")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Long startDate;
    private Long endDate;

    public Term(String title, Long startDate, Long endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
