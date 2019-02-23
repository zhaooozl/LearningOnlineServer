package com.example.learn.entity.student.exam;

import java.util.List;

public class ExamLayoutBean {
    private int layouttype;
    private String examName;
    private String teacherName;
    private String questionId;
    private int score;
    private String title;
    private String question;
    private List<Option> options;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getLayouttype() {
        return layouttype;
    }

    public void setLayouttype(int layouttype) {
        this.layouttype = layouttype;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "ExamLayoutBean{" +
                "layouttype=" + layouttype +
                ", examName='" + examName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", score=" + score +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
