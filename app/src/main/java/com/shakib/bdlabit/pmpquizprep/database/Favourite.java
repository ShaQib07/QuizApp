package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;

public class Favourite extends RealmObject {

    public Favourite() {
    }

    String subName;
    int qsnNumber;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(int qsnNumber) {
        this.qsnNumber = qsnNumber;
    }
}
