package com.example.academictracker;

public class PersonalModel {
    int id;
    String name,dob,gender,email,phone,address;
    byte[] image;

    public PersonalModel(int id, String name, String dob, String gender, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getImage() {
        return image;
    }
}
