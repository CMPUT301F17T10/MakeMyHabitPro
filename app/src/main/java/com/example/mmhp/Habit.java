package com.example.mmhp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yidingfan on 2017-10-13.
 */


public class Habit implements Comparable<Habit>{

//    private String name;
//
//    private Date startDate;
//
//    private Date lastActive;
//
//    private ArrayList doDate;
//
//    private int htype;
//
//    private EventList events = new EventList();
//
//    private int owner;
//
//    private Boolean done;

    private String userId;

    private String title;
    private String reason;
    private String comment;
    private String type;
    private String startDate;
    private Date lastActive;
  //  private HabitR.HabitEntity.UserHabitEntity.PlanEntity plan;



    private boolean Sun;
    private boolean Mon;
    private boolean Tue;
    private boolean Wen;
    private boolean Thu;
    private boolean Fri;
    private boolean Sat;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Habit(String title, String reason, String comment, String type, String startDate){
//        this.owner = uid;
//        this.startDate = new Date();
//        this.htype = htype;
//        this.doDate = doDate;
//        this.done = false;
        this.title = title;
        this.reason = reason;
        this.comment = comment;
        this.type = type;
        this.startDate = startDate;
        this.lastActive = new Date();
//        this.userId =

        this.Sun = false;
        this.Mon = false;
        this.Tue = false;
        this.Wen = false;
        this.Thu = false;
        this.Fri = false;
        this.Sat = false;
    }

//    public void addEvent(Event e){
//        this.events.add(e);
//
//        try{
//        this.lastActive = dateFormat.parse(e.getHappend());
//        }catch (ParseException e1){
//            this.lastActive = new Date();
//        }
//    }

    public void setlastActive(){
        this.lastActive = new Date();
    }

    public Date getlastActive(){
        return lastActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String detail) {
        this.comment = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setSun(boolean sun) {
        Sun = sun;
    }

    public void setMon(boolean mon) {
        Mon = mon;
    }

    public void setTue(boolean tue) {
        Tue = tue;
    }

    public void setWen(boolean wen) {
        Wen = wen;
    }

    public void setThu(boolean thu) {
        Thu = thu;
    }

    public void setFri(boolean fri) {
        Fri = fri;
    }

    public void setSat(boolean sat) {
        Sat = sat;
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

//    public ArrayList getDoDate() {
//        return doDate;
//    }
//
//    public String getLastActive() {
//        return dateFormat.format(lastActive);
//    }
//
//    public EventList getEvents() {
//        return events;
//    }
//
//    public int getHtype() {
//        return htype;
//    }
//
//    public int getOwner() {
//        return owner;
//    }
//
//    public Boolean getDone() {return done;}
//
//    public void setDone() {this.done = true;}
//
//    public void setDoDate(ArrayList doDate) {
//        this.doDate = doDate;
//    }
//
//    public void setHtype(int htype) {
//        this.htype = htype;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Date getLastActive_Date(){
//        return  this.lastActive;
//    }

    @Override
    public int compareTo(Habit h) {
        Date d1 = this.lastActive;
        Date d2 = h.getlastActive();
        return d1.compareTo(d2);
    }
}
