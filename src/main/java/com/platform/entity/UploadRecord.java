package com.platform.entity;

import java.util.Date;

/**
 * @author luxx
 * @date 2017/11/16 0016
 */
public class UploadRecord {

    private long id;
    private Date created = new Date();
    private String competition;
    private String fileName;
    private String result;
    private String message;
    private String user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UploadRecord() {
    }


    public UploadRecord(String competition, String fileName, String result, String message, String user) {
        this.created = new Date();
        this.competition = competition;
        this.fileName = fileName;
        this.result = result;
        this.message = message;
        this.user = user;
    }
}
