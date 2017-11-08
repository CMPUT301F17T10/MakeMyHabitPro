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

    private String name;

    private Date startDate;

    private Date lastActive;

    private ArrayList doDate;

    private int htype;

    private EventList events = new EventList();

    private int owner;

    private Boolean done;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Habit(int uid, String name, int htype, ArrayList doDate){
        this.owner = uid;
        this.startDate = new Date();
        this.htype = htype;
        this.doDate = doDate;
        this.done = false;
    }

    public void addEvent(Event e){
        this.events.add(e);

        try{
        this.lastActive = dateFormat.parse(e.getHappend());
        }catch (ParseException e1){
            this.lastActive = new Date();
        }
    }

    public ArrayList getDoDate() {
        return doDate;
    }

    public String getLastActive() {
        return dateFormat.format(lastActive);
    }

    public EventList getEvents() {
        return events;
    }

    public int getHtype() {
        return htype;
    }

    public int getOwner() {
        return owner;
    }

    public Boolean getDone() {return done;}

    public void setDone() {this.done = true;}

    public void setDoDate(ArrayList doDate) {
        this.doDate = doDate;
    }

    public void setHtype(int htype) {
        this.htype = htype;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getLastActive_Date(){
        return  this.lastActive;
    }

    @Override
    public int compareTo(Habit h) {
        Date d1 = this.lastActive;
        Date d2 = h.getLastActive_Date();
        return d1.compareTo(d2);
    }
}
