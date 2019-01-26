package com.example.lenovo.hire_me;

public class User {

    public String name,dob,email,phone,branch,r,t;
    public User(){

    }

    public User(String branch, String dob, String email, String name, String phone,String r,String t) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.branch = branch;
        this.r=r;
        this.t=t;
    }
}
