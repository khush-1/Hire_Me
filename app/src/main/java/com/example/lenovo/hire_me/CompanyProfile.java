package com.example.lenovo.hire_me;

public class CompanyProfile {

    public String position,cpi,ctc,deadline,preference;

    public CompanyProfile()
    {

    }
    public CompanyProfile(String postion1,String cpi1,String ctc1,String deadline1,String preference1)
    {
        this.position = postion1;
        this.cpi = cpi1;
        this.ctc = ctc1;
        this.deadline = deadline1;
        this.preference = preference1;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCpi() {
        return cpi;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}



