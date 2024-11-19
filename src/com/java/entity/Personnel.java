package com.java.entity;

import java.util.List;

public class Personnel {

    private Long id;
    private String username;
    private Long personnelCode;
    private String email;
    private String mobile;
    private List<Leave> leaveList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(Long personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Leave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<Leave> leaveList) {
        this.leaveList = leaveList;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", personnelCode=" + personnelCode +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", leaveList=" + leaveList +
                '}';
    }
}
