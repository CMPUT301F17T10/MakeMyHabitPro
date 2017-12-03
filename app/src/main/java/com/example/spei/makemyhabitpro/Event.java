/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by spei on 11/17/17.
 */

/**
 *Represents a event
 *
 * @author team 10
 * @version  2.0
 * @since 1.0
 */

public class Event implements Comparable<Event>{
    private String id;
    private String UID;
    private Habit habit;
    private Date happend;
    private String img;
    private String owner_comment;
    private double lat;
    private double lng;
    private ArrayList<String> pub_comment;


    public Event(Habit habit, String comment, String id, String UID) {
        this.habit = habit;
        this.id = id;
        this.UID = UID;
        this.owner_comment = comment;
        happend = new Date();
        this.lat = 200;
        this.lng = 200;

        pub_comment = new ArrayList<String>();
    }




//    public void pub_comment(String comment){
//        if(this.pub_comment.isEmpty()){
//            this.pub_comment=new ArrayList<String>();
//        }
//        this.pub_comment.add(comment);
//    }

    /**
     * a bunch of getter and setter
     * @return
     */
    public String getId(){
        return this.id;
    }
    public String getUID() {return this.UID;}
    public String getOwner_comment(){
        return this.owner_comment;
    }
    public Habit getHabit(){
        return this.habit;
    }
    public String getHappend(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(this.happend);
    }
    public Date getHabitDate(){
        return this.happend;
    }
    public ArrayList<String> getPub_comment(){
        return this.pub_comment;
    }
    public void setPub_comment(ArrayList<String> pub_comment){this.pub_comment = pub_comment;}
    public String getImg(){
        return this.img;
    }


    public void setImg(String img) {
        this.img = img;
    }

    public void setOwner_comment(String owner_comment) {
        this.owner_comment = owner_comment;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * modify the form of out put
     * @return
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return "Habit title: "+habit.getTitle()+", Comment: "+owner_comment+" \n"+dateFormat.format(happend);

        }

    /**
     * It has been used for sort method
     * @param e
     * @return
     */
    @Override
    public int compareTo(Event e) {
        Date d1=this.happend;
        Date d2=e.getHabitDate();
        return d1.compareTo(d2);
    }



}