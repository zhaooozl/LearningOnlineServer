package com.example.learn.entity.student;

public class SubjectRespBean {

    private int subjectId;
    private long userId;
    private String name;
    private String desc;
    private String courseware;

    public SubjectRespBean(int subjectId, long userId, String name, String desc, String courseware) {
        this.subjectId = subjectId;
        this.userId = userId;
        this.name = name;
        this.desc = desc;
        this.courseware = courseware;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCourseware() {
        return courseware;
    }

    public void setCourseware(String courseware) {
        this.courseware = courseware;
    }
}
