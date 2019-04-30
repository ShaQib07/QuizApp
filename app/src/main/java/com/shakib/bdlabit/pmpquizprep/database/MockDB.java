package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MockDB extends RealmObject {

    @PrimaryKey
    String mockupName;
    String subjectName;
    RealmList<QuestionMarkDB> questionMarkDBS;

    String correctAns;
    String wrongAns;
    String status;

    public MockDB() {
    }

    public String getMockupName() {
        return mockupName;
    }

    public void setMockupName(String mockupName) {
        this.mockupName = mockupName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public RealmList<QuestionMarkDB> getQuestionMarkDBS() {
        return questionMarkDBS;
    }

    public void setQuestionMarkDBS(RealmList<QuestionMarkDB> questionMarkDBS) {
        this.questionMarkDBS = questionMarkDBS;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getWrongAns() {
        return wrongAns;
    }

    public void setWrongAns(String wrongAns) {
        this.wrongAns = wrongAns;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
