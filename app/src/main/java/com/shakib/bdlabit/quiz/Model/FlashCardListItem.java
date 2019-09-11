package com.shakib.bdlabit.quiz.Model;

public class FlashCardListItem {

    String FlashCardName;
    boolean isCompleted;

    public FlashCardListItem() {
    }

    public String getFlashCardName() {
        return FlashCardName;
    }

    public void setFlashCardName(String flashCardName) {
        FlashCardName = flashCardName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
