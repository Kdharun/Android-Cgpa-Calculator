package com.example.academictracker;

public class CgpaModel {
    private int semester;
    private double total_credits;
    private double gpa;
    private double cgpa;

    public CgpaModel(int semester, double total_credits, double gpa, double cgpa) {
        this.semester = semester;
        this.total_credits = total_credits;
        this.gpa = gpa;
        this.cgpa = cgpa;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getTotal_credits() {
        return total_credits;
    }

    public void setTotal_credits(int total_credits) {
        this.total_credits = total_credits;
    }

    public double getGpa() { return gpa; }

    public void setGpa(int gpa) {
        this.gpa = gpa;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(int cgpa) {
        this.cgpa = cgpa;
    }
}