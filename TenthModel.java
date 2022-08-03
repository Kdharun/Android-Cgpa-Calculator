package com.example.academictracker;

public class TenthModel {
    private int id;
    private String regNo;
    private String passedout;
    private int lang,eng,maths,sci,soc,total;
    private double percentage;

    public TenthModel(int id, String regNo, String passedout, int lang, int eng, int maths, int sci, int soc, int total, double percentage) {
        this.id = id;
        this.regNo = regNo;
        this.passedout = passedout;
        this.lang = lang;
        this.eng = eng;
        this.maths = maths;
        this.sci = sci;
        this.soc = soc;
        this.total = total;
        this.percentage = percentage;
    }

    public TenthModel(){}

    public int getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getPassedout() {
        return passedout;
    }

    public int getLang() {
        return lang;
    }

    public int getEng() {
        return eng;
    }

    public int getMaths() {
        return maths;
    }

    public int getSci() {
        return sci;
    }

    public int getSoc() {
        return soc;
    }

    public int getTotal() {
        return total;
    }

    public double getPercentage() {
        return percentage;
    }

}
