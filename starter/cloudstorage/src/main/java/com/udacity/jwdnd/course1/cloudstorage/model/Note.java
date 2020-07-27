package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid, userid;
    private String notetitle, notedescription;

    public Note(Integer noteid, Integer userid, String notetitle, String description) {
        this.noteid = noteid;
        this.userid = userid;
        this.notetitle = notetitle;
        this.notedescription = description;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public Integer getUserid() {
        return userid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}