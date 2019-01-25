package com.example.lenovo.hire_me;

public class Posts {
    public String date,name,post,time,uid;
    public Posts(){

    }

    public Posts(String date, String name, String post, String time, String uid) {
        this.date = date;
        this.name = name;
        this.post = post;
        this.time = time;
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
