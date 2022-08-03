package com.example.academictracker;

public class SemesterModel {
    private String subCode;
    private String subName;
    private double subCredit;
    private String grade;

    public SemesterModel(String subcode, String subname, double subcredit, String grade) {
        this.subCode = subcode;
        this.subName = subname;
        this.subCredit = subcredit;
        this.grade = grade;
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

    public double getSubCredit() {
        return subCredit;
    }

    public void setSubCredit(double subCredit) {
        this.subCredit = subCredit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
