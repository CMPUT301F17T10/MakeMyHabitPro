/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by spei on 11/17/17.
 */

public class Habit implements Comparable<Habit>{



    private String userId;
    private String title;
    private String reason;
    private String detail;
    private String type;
    private String startDate;
    private boolean Sun;
    private boolean Mon;
    private boolean Tue;
    private boolean Wen;
    private boolean Thu;
    private boolean Fri;
    private boolean Sat;
    private Date lastActive;
    private int finished=0;
    public Habit(){}
    public Habit(String userId, String title, String reason, String detail, String type,String startDate) {
        this.lastActive = new Date();
        this.userId = userId;
        this.title = title;
        this.reason = reason;
        this.detail = detail;
        this.type = type;
        this.startDate = startDate;
    }
    public void doIt(){
        this.finished=this.finished+1;
    }
    public int getFinished(){
        return this.finished;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setSun(boolean Sun) {
        this.Sun = Sun;
    }

    public void setMon(boolean Mon) {
        this.Mon = Mon;
    }

    public void setTue(boolean Tue) {
        this.Tue = Tue;
    }

    public void setWen(boolean Wen) {
        this.Wen = Wen;
    }

    public void setThu(boolean Thu) {
        this.Thu = Thu;
    }

    public void setFri(boolean Fri) {
        this.Fri = Fri;
    }

    public void setSat(boolean Sat) {
        this.Sat = Sat;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getStartDate() {
        return startDate;
    }

    public boolean getSun() {
        return Sun;
    }

    public boolean getMon() {
        return Mon;
    }

    public boolean getTue() {
        return Tue;
    }

    public boolean getWen() {
        return Wen;
    }

    public boolean getThu() {
        return Thu;
    }

    public boolean getFri() {
        return Fri;
    }

    public boolean getSat() {
        return Sat;
    }

    public String getLastActive() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(this.lastActive);
    }

    @Override
    public String toString() {

        return String.format("%s, %s, %s \n%s", title, type, lastActive,detail);
    }

    @Override
    public int compareTo(Habit h) {
        Date d1 = this.lastActive;
        Date d2 = h.getlastActive();
        return d1.compareTo(d2);
    }

    public Date getlastActive() {
        return lastActive;
    }

    public void setLastActive(SimpleDateFormat simpleDateFormat) {
    }
}