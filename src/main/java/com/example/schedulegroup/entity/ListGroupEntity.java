package com.example.schedulegroup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_group")
public class ListGroupEntity {

    @Id
    private int id;
    private String name;
    private int facultyId;
    private String facultyName;
    private int specialityDepartmentEducationFormId;
    private String specialityName;
    private int course;
    private String calendarId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getSpecialityDepartmentEducationFormId() {
        return specialityDepartmentEducationFormId;
    }

    public void setSpecialityDepartmentEducationFormId(int specialityDepartmentEducationFormId) {
        this.specialityDepartmentEducationFormId = specialityDepartmentEducationFormId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public ListGroupEntity() {
        // cause of Gson()
    }
}