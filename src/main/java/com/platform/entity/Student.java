package com.platform.entity;

/**
 * Created by luxx on 2017/11/15 0015.
 */
public class Student {

    private long id;
    private String serialNo;
    private String name;
    private String sex;
    private String grade;
    private String contact;
    private String identity;
    private String school;
    private String home;
    private String groupNo;
    private String teacher;
    private String source;
    private String ticketNo;
    private String roomNo;
    private String seatNo;
    private String examTime;
    private String examLocation;
    private int competitionId;
    private boolean state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Student(String serialNo, String name, String sex, String grade, String contact, String identity, String school, String home, String groupNo, String teacher, String source, String ticketNo, String roomNo, String seatNo, String examTime, String examLocation, int competitionId, boolean state) {
        this.serialNo = serialNo;
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.contact = contact;
        this.identity = identity;
        this.school = school;
        this.home = home;
        this.groupNo = groupNo;
        this.teacher = teacher;
        this.source = source;
        this.ticketNo = ticketNo;
        this.roomNo = roomNo;
        this.seatNo = seatNo;
        this.examTime = examTime;
        this.examLocation = examLocation;
        this.competitionId = competitionId;
        this.state = state;
    }

    public Student() {
    }

    public Student(String name, String identity, int competitionId) {
        this.name = name;
        this.identity = identity;
        this.competitionId = competitionId;
    }
}
