package com.example.lenovo.hire_me;

public class UserProfile {
    public String branch,dob,email,name,phone;

    public UserProfile()
    {

    }

    public UserProfile(String branch, String dob, String email, String name, String phone) {
        this.branch = branch;
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
