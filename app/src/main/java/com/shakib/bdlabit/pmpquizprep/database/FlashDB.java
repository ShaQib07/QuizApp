package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FlashDB extends RealmObject {

    @PrimaryKey
    String FlashCardName;
    String subjectName;

    public FlashDB() {
    }

    public String getFlashCardName() {
        return FlashCardName;
    }

    public void setFlashCardName(String flashCardName) {
        FlashCardName = flashCardName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
