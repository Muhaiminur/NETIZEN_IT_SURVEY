package com.muhaiminurabir.netizen_survey.DATABASE;

import io.realm.RealmObject;

public class STUDENT extends RealmObject {
    String name;
    String gender;
    String father_name;
    String mother_name;
    String birth_date;
    String school_name;
    String group;

    public STUDENT() {

    }

    public STUDENT(String name, String gender, String father_name, String mother_name, String birth_date, String school_name, String group) {
        this.name = name;
        this.gender = gender;
        this.father_name = father_name;
        this.mother_name = mother_name;
        this.birth_date = birth_date;
        this.school_name = school_name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "STUDENT{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", father_name='" + father_name + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", school_name='" + school_name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
