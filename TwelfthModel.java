package com.example.academictracker;

public class TwelfthModel {
    private int id;
    private String regNo;
    private String passedout;
    private int lang, eng, maths, phy, chem, biocs;
    private int total;
    private double percentage;

    public TwelfthModel(int id, String regNo, String passedout, int lang, int eng, int maths, int phy, int chem, int biocs, int total, double percentage) {
        this.id = id;
        this.regNo = regNo;
        this.passedout = passedout;
        this.lang = lang;
        this.eng = eng;
        this.maths = maths;
        this.phy = phy;
        this.chem = chem;
        this.biocs = biocs;
        this.total = total;
        this.percentage = percentage;
    }

    public TwelfthModel() {
    }

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

    public int getPhy() {
        return phy;
    }

    public int getChem() {
        return chem;
    }

    public int getBiocs() {
        return biocs;
    }

    public int getTotal() {
        return total;
    }

    public double getPercentage() {
        return percentage;
    }
}
