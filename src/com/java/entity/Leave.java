package com.java.entity;

import java.time.LocalDate;

public class Leave {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long personnelId;
    private Personnel personnel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
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
                ", personnelId=" + personnelId +
                ", personnel=" + (personnel != null ? personnel.getUsername() : "null") +
                '}';
    }
}
