package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;

public class Favourite extends RealmObject {

    public Favourite() {
    }

    String subjectName;
    QuestionDB question;

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
