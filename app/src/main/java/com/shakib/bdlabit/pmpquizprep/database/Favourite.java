package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favourite extends RealmObject {

    public Favourite() {
    }

    @PrimaryKey
    String id;
    String subjectName;
    QuestionDB question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public QuestionDB getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDB question) {
        this.question = question;
    }
}
