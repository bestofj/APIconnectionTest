package com.example.apiconnectiontest.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;

public class StudentInfo implements Serializable {
    private int id;
    private int student_id;
    private int years;
    private int school_id;
    private int grade;
    private int class_number;
    private int number;
    private Calendar created_at;
    private Calendar updated_at;

    public static StudentInfo getInfoFromJson(JSONObject json) {
        StudentInfo info = new StudentInfo();

        try {
            info.setId(json.getInt("id"));
            info.setYears(json.getInt("years"));
            info.setGrade(json.getInt("grade"));
            info.setClass_number(json.getInt("class_number"));
            info.setNumber(json.getInt("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }

    public StudentInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClass_number() {
        return class_number;
    }

    public void setClass_number(int class_number) {
        this.class_number = class_number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }

    public Calendar getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Calendar updated_at) {
        this.updated_at = updated_at;
    }
}
