package com.example.lenovo.hire_me;

public class UserProfile {
    public String username,usercontact,useremail,userdob,userbranch,userresu,usertra;

    public UserProfile()
    {

    }
    public UserProfile(String username,String usercontact,String useremail,String userdob,String userbranch,String userresu,String usertra){
        this.username = username;
        this.useremail = useremail;
        this.userbranch = userbranch;
        this.userdob = userdob;
        this.usercontact = usercontact;
        this.userresu=userresu;
        this.usertra=usertra;
    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getUsercontact() {
        return usercontact;
    }

//    public void setUsercontact(String usercontact) {
//        this.usercontact = usercontact;
//    }

    public String getUseremail() {
        return useremail;
    }

//    public void setUseremail(String useremail) {
//        this.useremail = useremail;
//    }

    public String getUserdob() {
        return userdob;
    }

//    public void setUserdob(String userdob) {
//        this.userdob = userdob;
//    }

    public String getUserbranch() {
        return userbranch;
    }

//    public void setUserbranch(String userbranch) {
//        this.userbranch = userbranch;
//    }
    public String getUserresu()
    {
        return userresu;
    }
    public String getUsertra()
    {
        return usertra;
    }
}
