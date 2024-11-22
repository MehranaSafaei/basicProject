package com.java.entity;

import java.time.LocalDate;

public class Leave {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long personnelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate(LocalDate startDate) {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate(LocalDate endDate) {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", personnel=" + personnelId +
                '}';
    }
}
