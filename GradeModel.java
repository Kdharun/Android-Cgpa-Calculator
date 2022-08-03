package com.example.academictracker;

public class GradeModel {
    private String subCode,subName,grade;

    public GradeModel(int semester, String subCode, String subName, double subCredit, String grade) {
        this.subCode = subCode;
        this.subName = subName;
        this.grade = grade;
        this.subCredit = subCredit;
        this.semester = semester;
    }

    private double subCredit;
    private int semester;

    public GradeModel(String subCode, String subName,double subCredit, String grade) {
        this.subCode = subCode;
        this.subName = subName;
        this.grade = grade;
        this.subCredit = subCredit;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getSubCredit() {
        return subCredit;
    }

    public void setSubCredit(double subCredit) {
        this.subCredit = subCredit;
    }
}
