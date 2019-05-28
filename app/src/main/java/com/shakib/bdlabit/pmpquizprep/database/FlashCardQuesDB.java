package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;

public class FlashCardQuesDB extends RealmObject {

    private String qsn;
    private String ans;

    public FlashCardQuesDB() {
    }

    public FlashCardQuesDB(String qsn, String ans) {
        this.qsn = qsn;
        this.ans = ans;
    }

    public String getQsn() {
        return qsn;
    }

    public void setQsn(String qsn) {
        this.qsn = qsn;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}




