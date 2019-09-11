package com.shakib.bdlabit.quiz.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PracticeDB extends RealmObject {

    @PrimaryKey
    String PracticeName;
    String subjectName;

    public PracticeDB() {
    }

    public String getPracticeName() {
        return PracticeName;
    }

    public void setPracticeName(String practiceName) {
        PracticeName = practiceName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
