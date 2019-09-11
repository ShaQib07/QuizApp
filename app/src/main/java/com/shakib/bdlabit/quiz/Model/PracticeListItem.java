package com.shakib.bdlabit.quiz.Model;

public class PracticeListItem {

    String PracticeName;
    boolean isCompleted;

    public PracticeListItem() {
    }

    public String getPracticeName() {
        return PracticeName;
    }

    public void setPracticeName(String practiceName) {
        PracticeName = practiceName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
