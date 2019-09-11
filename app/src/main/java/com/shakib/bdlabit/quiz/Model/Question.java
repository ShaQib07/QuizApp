package com.shakib.bdlabit.quiz.Model;

public class Question {

    private String correctAns, explaination, mark, options1, options2, options3, options4, qsn, qsnNumber, chapter;

    public Question() {
    }

    public Question(String correctAns, String explaination, String mark, String options1, String options2, String options3, String options4, String qsn, String qsnNumber) {
        this.correctAns = correctAns;
        this.explaination = explaination;
        this.mark = mark;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
        this.options4 = options4;
        this.qsn = qsn;
        this.qsnNumber = qsnNumber;
    }

    public Question(String correctAns, String explaination, String mark, String options1, String options2, String options3, String options4, String qsn, String qsnNumber, String chapter) {
        this.correctAns = correctAns;
        this.explaination = explaination;
        this.mark = mark;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
        this.options4 = options4;
        this.qsn = qsn;
        this.qsnNumber = qsnNumber;
        this.chapter = chapter;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOptions1() {
        return options1;
    }

    public void setOptions1(String options1) {
        this.options1 = options1;
    }

    public String getOptions2() {
        return options2;
    }

    public void setOptions2(String options2) {
        this.options2 = options2;
    }

    public String getOptions3() {
        return options3;
    }

    public void setOptions3(String options3) {
        this.options3 = options3;
    }

    public String getOptions4() {
        return options4;
    }

    public void setOptions4(String options4) {
        this.options4 = options4;
    }

    public String getQsn() {
        return qsn;
    }

    public void setQsn(String qsn) {
        this.qsn = qsn;
    }

    public String getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(String qsnNumber) {
        this.qsnNumber = qsnNumber;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
