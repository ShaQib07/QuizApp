package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;

public class MockResultDB extends RealmObject {

    String correctAns;
    String wrongAns;
    String status;

    public MockResultDB() {
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
