package com.shakib.bdlabit.pmpquizprep.database;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SubjectDB extends RealmObject {

    String subName;
    RealmList<QuestionDB> questionDBRealmList;

    public SubjectDB() {
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public RealmList<QuestionDB> getQuestionDBRealmList() {
        return questionDBRealmList;
    }

    public void setQuestionDBRealmList(RealmList<QuestionDB> questionDBRealmList) {
        this.questionDBRealmList = questionDBRealmList;
    }
}
